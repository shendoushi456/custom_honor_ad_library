package com.base.imagefilestego

import com.base.imagefilestego.internal.MemoryStorage
import com.base.imagefilestego.internal.PngOutput
import com.base.imagefilestego.internal.TempLease
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.IOException
import java.security.MessageDigest

class StorageTest {
    @get:Rule val folder = TemporaryFolder()

    @Test fun memoryOnlyResultsNeverCreateCacheFiles() {
        val root = File(folder.root, "cache")
        val data = ByteArray(180_000) { (it * 31).toByte() }
        PngOutput(root, 8, false, 200_000) {}.use { output ->
            output.write(data)
            val (storage, digest) = output.finish()
            storage.use {
                it.open().use { input -> assertArrayEquals(data, input.readBytes()) }
                assertArrayEquals(MessageDigest.getInstance("SHA-256").digest(data), digest)
            }
        }
        assertFalse(root.exists())
    }

    @Test fun spilledOutputIsProtectedUntilResultCloses() {
        val root = folder.newFolder("cache")
        val first = byteArrayOf(1, 2, 3)
        val second = ByteArray(100_000) { it.toByte() }
        val output = PngOutput(root, 4, true, 4) {}
        output.write(first)
        output.write(second)
        val (storage, digest) = output.finish()
        output.close()
        assertEquals(1, sessions(root).size)
        TempLease.cleanup(root)
        assertEquals(1, sessions(root).size)
        val artifact = StegoArtifact("result.png", "image/png", storage, digest)
        val stream = artifact.openStream()
        assertArrayEquals(first + second, stream.readBytes())
        artifact.close()
        artifact.close()
        assertTrue(sessions(root).isEmpty())
        try { stream.read(); fail("关闭结果应关闭已打开的流") } catch (_: IOException) { }
        try { artifact.openStream(); fail("已释放的结果不能重新打开") } catch (_: IOException) { }
    }

    @Test fun failureAndCancellationDeleteSpilledFiles() {
        val root = folder.newFolder("cache")
        var cancelled = false
        val output = PngOutput(root, 4, true, 4) { if (cancelled) throw kotlinx.coroutines.CancellationException() }
        try {
            output.use {
                it.write(ByteArray(20))
                cancelled = true
                it.write(1)
            }
            fail("应响应取消")
        } catch (_: kotlinx.coroutines.CancellationException) { }
        assertTrue(sessions(root).isEmpty())
    }

    @Test fun staleSessionsAreRemovedAndUnrelatedFilesArePreserved() {
        val root = folder.newFolder("cache")
        val stale = File(root, "session-old").apply { mkdir() }
        File(stale, "result.png").writeBytes(byteArrayOf(1))
        File(stale, "lease.lock").createNewFile()
        val unrelated = File(root, "user.txt").apply { writeText("保留") }
        val active = TempLease.create(root)
        assertFalse(stale.exists())
        TempLease.cleanup(root)
        assertTrue(unrelated.exists())
        assertEquals(1, sessions(root).size)
        active.close()
    }

    @Test fun plaintextMemoryIsClearedWhenArtifactCloses() {
        val bytes = byteArrayOf(1, 2, 3)
        val artifact = StegoArtifact("a.bin", "application/octet-stream", MemoryStorage.own(bytes), ByteArray(32))
        artifact.close()
        assertArrayEquals(ByteArray(3), bytes)
    }

    @Test fun memoryOnlyModeEnforcesBudgetWithoutSpilling() {
        val root = File(folder.root, "cache")
        try {
            PngOutput(root, 4, false, 16) {}.use { it.write(ByteArray(17)) }
            fail("应在超过预算前拒绝分配")
        } catch (error: StegoException) { assertEquals(StegoError.MEMORY_LIMIT, error.code) }
        assertFalse(root.exists())
    }

    private fun sessions(root: File) = root.listFiles()?.filter { it.name.startsWith("session-") } ?: emptyList()
}
