# 图片藏文件

Android 8.0+ 的离线图片隐写库，提供 Compose Activity 和独立的 Kotlin/Java API。每张图片存放一个任意二进制文件，密码必填，输出为不透明 PNG。新图片默认使用 v2 加密头部，仍可提取旧 v1 图片；旧版工具不能提取 v2 图片。SDK 不依赖 Activity、服务器、Root 或 NDK，不申请存储权限。

## 接入与页面

本模块位于 `modules/custom_honor_ad_library/image_file_stego`，由宿主工程注册：

```groovy
// settings.gradle
include ':image_file_stego'
project(':image_file_stego').projectDir = file('./modules/custom_honor_ad_library/image_file_stego')

// 使用方的 build.gradle
implementation project(':image_file_stego')
```

直接启动：

```kotlin
import com.base.imagefilestego.ImageFileStegoActivity
import com.base.imagefilestego.StegoMode

ImageFileStegoActivity.start(context)
ImageFileStegoActivity.start(context, StegoMode.EXTRACT)
```

Java 同样可直接调用 `ImageFileStegoActivity.start(context, StegoMode.EXTRACT)`。Activity 为 `exported=false`，只供集成此库的应用启动；不提供跨 App Binder 服务。

页面包含“图片添加文件”“图片提取文件”两项，使用系统 SAF 选择和导出。密码不保存；旋转屏幕时 ViewModel 保留正在执行的任务和结果，密码输入框会清空。进程退出后重新选择文件。保存完成后回读校验 SHA-256，失败时尝试删除本次创建的不完整文档；若提供方拒绝删除，需要用户在所选目录中清理。

进度展示在模块内通过 `AndroidView` 使用系统水平 `ProgressBar`，支持不确定进度与实际处理进度，保留阶段提示和取消操作。当前工程使用的 Material3 1.1.2 与 Compose Animation Core 1.6.0 在不确定进度条的关键帧动画处存在二进制兼容问题，会触发 `NoSuchMethodError`；本模块避开该调用，无需修改宿主配置或升级 Compose 依赖。该适配不涉及图片编解码、隐写协议和无 UI 接口。

## 无 UI 调用

`StegoSource` 支持 `Uri`、`ByteArray`、每次返回新输入流的 `StreamFactory`。流工厂必须始终提供相同内容；字节数组为了避免复制，由调用方保证处理期间不修改。`fromUri` 会查询名称和大小，建议在 IO 线程创建。未知文件大小会在读取时进行有界检查。

Kotlin 示例，`outputUri` 由调用方预先取得，不会触发任何界面：

```kotlin
import com.base.imagefilestego.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun hideFile(context: android.content.Context,
                     coverUri: android.net.Uri,
                     fileUri: android.net.Uri,
                     outputUri: android.net.Uri,
                     password: CharArray) {
    withContext(Dispatchers.IO) {
        val sdk = ImageFileStego(context)
        val cover = StegoSource.fromUri(context, coverUri)
        val file = StegoSource.fromUri(context, fileUri)
        val info = sdk.inspect(cover, file.displayName)
        check(info.withinLimits) { info.limitation ?: "图片超限" }
        sdk.embed(cover, file, password).use { png ->
            context.contentResolver.openOutputStream(outputUri, "wt")!!.use { output ->
                png.writeTo(output)
            }
        }
    }
}

suspend fun recover(context: android.content.Context,
                    image: StegoSource,
                    password: CharArray,
                    consume: (String, java.io.InputStream) -> Unit) {
    withContext(Dispatchers.IO) {
        ImageFileStego(context).extract(image, password).use { file ->
            file.openStream().use { input -> consume(file.fileName, input) }
        }
    }
}
```

`embed` 返回前已重新解码 PNG、验证认证标签及原文件 SHA-256；`extract` 在 GCM 认证成功后才返回明文。`writeTo` 只写入调用方指定的流，不关闭该输出流。对于不可回读的输出流，SDK 无法验证目的端；调用方可在写入后读取目标并与结果的 `sha256` 比较，内置页面已经执行该检查。

Java 示例，默认回调运行在后台线程：

```java
ImageFileStego sdk = new ImageFileStego(context);
StegoSource cover = StegoSource.fromBytes(pngOrJpegBytes, "cover.png");
StegoSource file = StegoSource.fromBytes(fileBytes, "资料.bin");
char[] password = obtainPassword();

StegoTask task = sdk.embedAsync(cover, file, password,
        new StegoCallback<StegoArtifact>() {
            @Override public void onSuccess(StegoArtifact result) {
                try (StegoArtifact png = result;
                     java.io.InputStream input = png.openStream()) {
                    consumePngStream(input);
                } catch (java.io.IOException error) {
                    handleIoError(error);
                }
            }
            @Override public void onError(StegoException error) {
                handleError(error.getCode(), error.getMessage());
            }
            @Override public void onCancelled() {
                handleCancelled();
            }
        });
java.util.Arrays.fill(password, '\0');
// 不再需要处理时调用 task.cancel()。
```

提取对应 `extractAsync(image, password, callback)`；容量查询对应 `inspectAsync(image, fileName, callback)`。异步接口会同步复制传入密码，因此返回后可立即清空调用方数组。挂起接口调用完成后再清空调用方密码；库会清空自己拥有的副本。

可选 `StegoProgressListener` 回调阶段与进度；Java 异步结果可指定 `Executor`。默认回调和进度均不在 UI 线程。调用方传入的 Executor 必须能够接收任务。协程取消或 `StegoTask.cancel()` 会停止处理并清理结果；PBKDF2、部分底层解码操作在返回后响应取消，不能保证瞬间中断。

`StegoException.code` 可区分不支持的格式、容量不足、内存不足、输入无效、IO 错误和认证失败。v2 没有公开格式标记，普通图片、密码错误和认证数据损坏统一返回 `AUTHENTICATION_FAILED`，提示“没有可提取的文件、密码错误或图片数据已损坏”。`NO_PAYLOAD` 枚举保留兼容，调用方不能依靠它判断 v2 图片是否包含文件。Kotlin 的取消以 `CancellationException` 传播，不应当作为普通失败吞掉。

## 内存与文件生命周期

默认配置：

```kotlin
val sdk = ImageFileStego(context, StegoOptions(
    allowTemporaryFiles = true,
    memoryThresholdBytes = 8 * 1024 * 1024,
    maxWorkingMemoryBytes = 128L * 1024 * 1024,
    maxPixels = 24_000_000
))
```

- 图片逐行读取，随机读写通过 RGB 最低位集合完成，不分配整图 `IntArray` 或全量随机索引数组。1200 万像素的 Bitmap、两个位集合与行缓冲基础占用约 54 MiB；总预算还计入文件、密文、PNG 缓冲、EXIF 旋转和额外余量。
- 实际预算不超过配置值、最大堆内存的一半及当前可用内存减去 16 MiB。重任务在进程内串行执行，超限提前拒绝；预算是保守估算，不能替代具体设备的性能验证。
- 输入图片和输出 PNG 分别限制为 128 MiB，载体最多 2400 万像素。图像压缩率、透明度、设备内存会影响可处理上限，不会自动缩小原图。
- 文件内容、密码、解密明文始终在内存中。超过阈值的 PNG 使用 `cacheDir/image_file_stego/session-随机标识/result.png`，该 PNG 内的文件载荷已经加密；不复制输入文件到本地。
- `StegoArtifact` 必须 `close()`，否则会持续持有缓冲或 PNG 临时文件。关闭结果同时关闭它已打开的输入流，内存结果清零。每次开始操作都会清理失去会话锁的残留目录，活跃结果不被清理。
- 正常完成后的临时结果在调用方关闭时删除；失败、取消和页面退出时自动释放。强杀进程时无法执行 finally，残留由下一次操作或 `cleanupTemporaryFiles()` 清理。空的 `registry.lock` 只是跨进程清理锁，不存放文件内容。
- 配置 `allowTemporaryFiles=false` 可禁止生成 PNG 临时文件；此时内存容纳不下输出会返回 `MEMORY_LIMIT`。JVM、加密 Provider 和系统解码器可能保留内部副本，数组清零不等于可证明的安全擦除。

## 容量与协议 v2

使用 RGB 每通道 1 位，载荷最多占去除头部后通道的 50%，用于限制去重采样耗时。最多可隐藏的文件字节数：

```text
floor((width * height * 3 - 640) / 16)
    - 16               // 文件内容的 GCM 标签
    - 11               // 名称长度、原文件长度、压缩标志
    - UTF8(文件名).size
```

按 64 字节文件名计算，1920×1080 约 0.371 MiB，3840×2160 约 1.483 MiB，4000×3000 为 2249869 字节（约 2.146 MiB）。同尺寸和文件名下，v2 最大文件容量比 v1 少 8 字节，`inspect` 返回 v2 容量。文件类型不限，但不压缩、不做多图分片。中文文件名支持，名称不能是空白、`.`、`..`，不能包含路径分隔符和控制字符，UTF-8 长度最多 1024 字节。

所有整数大端，每个字节高位先写；像素从左到右、从上到下，通道顺序 R、G、B。头部固定 80 字节，占前 640 个通道：

| 偏移 | 字节数 | 内容 |
|---|---:|---|
| 0 | 16 | 每次由 SecureRandom 生成的公开 salt |
| 16 | 12 | 每次随机生成的公开头部 GCM nonce |
| 28 | 36 | 加密元数据 |
| 64 | 16 | 头部 GCM 认证标签 |

上述前 28 字节称为引导信息。salt 与 nonce 不是密钥，不需要保密。没有固定的明文 `IFSG`、版本号、迭代次数、长度或零填充；随机数据仍有可能偶然出现任意字节序列。解密并认证后的 36 字节元数据为：

| 偏移 | 字节数 | 内容 |
|---|---:|---|
| 0 | 4 | 版本 2、KDF 1、位置算法 1、标志 0 |
| 4 | 4 | uint32(600000) |
| 8 | 12 | 独立随机生成的文件内容 GCM nonce |
| 20 | 8 | uint64 文件密文长度，包含文件内容的 16 字节 GCM 标签 |
| 28 | 8 | 全零保留字段 |

密码不 trim、不进行 Unicode 归一化；按 UTF-8 语义进行 PBKDF2-HMAC-SHA256，固定 600000 轮，输出 32 字节 Kmaster。引导阶段的 KDF 参数由协议实现固定，不从未认证数据读取。按 RFC 5869 使用空 salt（即 32 个零字节）做 HKDF-Extract；以下 ASCII info 分别扩展三把 32 字节密钥：

- `image-file/v2/header`：头部加密密钥。
- `image-file/v2/encryption`：文件内容加密密钥。
- `image-file/v2/positions`：像素位置密钥。

头部与文件内容均使用 AES-256-GCM，128 位认证标签，各自使用独立子密钥和随机 nonce。AAD 定义如下，所有字符串为 ASCII：

```text
头部 AAD = "image-file/v2/header" || 引导信息的 28 字节 || uint32(width) || uint32(height)
文件 AAD = "image-file/v2/payload" || 完整头部的 80 字节 || uint32(width) || uint32(height)
```

读取时先派生密钥、验证头部标签，再校验解密得到的版本、固定参数、保留字段和长度。长度必须在协议容量内且不少于 28 字节，校验完成前不按长度分配文件缓冲。文件密文认证同时绑定整个头部和图片尺寸。

文件明文结构沿用 v1：`uint16 文件名 UTF-8 字节数 | 文件名 | uint64 文件内容长度 | uint8 压缩标志(0) | 原文件字节`，不使用 Base64。位置随机流为：

```text
block[i] = HMAC-SHA256(Kpos,
    ASCII("image-file/v2/position-stream") || 文件内容 nonce ||
    uint32_be(width) || uint32_be(height) || uint64_be(i))
```

计数器从零开始，每块依次切分为 8 个无符号 uint32 大端候选。令 `N = 3*width*height - 640`，丢弃 `candidate >= floor(2^32/N)*N` 的候选，取 `candidate % N`，重复位置跳过，最终加 640 得到通道编号。每次生成新 salt 和两个 nonce，相同图片、文件和密码也会生成不同结果。

### v1 读取兼容与失败提示

新写入只使用 v2，提取接口自动兼容 v1，不需要调用方选择版本。读取时优先认证 v2；只有头部认证失败且发现符合旧格式的头部时才尝试 v1，防止 v2 随机 salt 恰好以 `IFSG` 开头被误判。候选格式仅有两种，普通图片和 v2 通常执行一次 KDF，旧 v1 图片执行两次；不根据图片数据无限尝试算法或迭代次数。生成后回读 v2 时复用本次密钥，但仍验证头部与文件内容的认证标签。

v1 的头部仍按以下格式读取，不写入新图片：

| 偏移 | 字节数 | 内容 |
|---|---:|---|
| 0 | 4 | ASCII `IFSG` |
| 4 | 4 | 版本 1、KDF 1、位置算法 1、标志 0 |
| 8 | 12 | uint32(600000)、uint32(0)、uint32(0) |
| 20 | 16 | salt |
| 36 | 12 | 文件内容 GCM nonce |
| 48 | 8 | uint64 文件密文长度 |
| 56 | 8 | 全零保留字段 |

v1 保留 512 个通道，容量公式中的 640 改为 512。KDF 不变，HKDF 标签为 `image-file/v1/encryption` 和 `image-file/v1/positions`；文件 AAD 为完整 64 字节头部加宽高，无字符串前缀；位置流标签为 `image-file/v1/position-stream`，可用通道数和偏移量按 512 计算。旧图片不会因升级自动移除明文头部；需要提取文件后重新生成 v2 图片。

v2 改善格式指纹和元数据暴露，不保证图片无法被发现，也不会消除弱密码被离线猜测的风险。普通图片没有可验证的明文标记，需要输入密码并执行 KDF 后尝试认证，无法可靠区分“没有隐藏内容”“密码错误”和“图片损坏”。

嵌入前处理 JPEG/PNG 的 EXIF 方向、sRGB 和白底透明合成；提取只接收不透明 PNG，不处理 EXIF、不旋转、不缩放。LSB 统计分析或与原图对比仍可能发现隐藏行为。截图、转 JPEG、滤镜、缩放都会破坏像素，传输时应作为原始文件发送。

## 构建与测试

在 LXToolsProject 根目录执行：

```bash
./gradlew :image_file_stego:testDebugUnitTest :image_file_stego:lintDebug \
  :image_file_stego:assembleDebug :image_file_stego:assembleDebugAndroidTest :app:assembleDebug
```

AAR 位于 `image_file_stego/build/outputs/aar/`。若使用裸 AAR，需要在接入方声明本模块 `build.gradle` 的运行依赖；推荐直接引用 Gradle project。

`ProtocolTest` 和 `V2ProtocolTest` 使用 Python hashlib/HMAC/cryptography 独立产生的 v1/v2 加密向量，覆盖中文与 emoji 密码、三把子密钥、头部逐字节篡改、AAD、随机位置、长度边界、失败提示、取消及密钥清理。JVM 测试还通过 ImageIO 读取 v1/v2 PNG 固定样本并提取文件，无需设备。`StorageTest` 覆盖内存路径、溢写、关闭、取消、陈旧目录和活跃目录保护。Android 测试资源包含独立生成的 v1/v2 协议 PNG 和 EXIF 旋转 JPEG。`tools/generate_v2_vector.py` 可重新生成 v2 固定向量和 PNG，需要 Python cryptography、Pillow，仅供开发验证。

设备测试代码已保留，接入方可自行运行：

```bash
./gradlew :image_file_stego:connectedDebugAndroidTest
```

设备测试覆盖 PNG 往返、Android Provider 固定向量、透明图、JPEG、EXIF、错误密码、篡改、容量上限、临时文件、无 UI 回调、Activity 重建和大图预算。另需在实际设备上确认 SAF 选择/取消/保存/权限失效、中文输入、处理过程中返回页面及跨手机恢复；推荐包含 API 26 和较新 Android 版本。
