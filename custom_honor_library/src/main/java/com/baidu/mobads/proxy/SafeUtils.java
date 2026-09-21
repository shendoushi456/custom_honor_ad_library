package com.baidu.mobads.proxy;

import android.content.Context;
import android.content.Intent;


import androidx.annotation.Keep;

public class SafeUtils {
    static {
//        System.loadLibrary("ccrash");
//        System.loadLibrary("7a9d064d");
    }


    public static void iitF(String file){
        System.load(file);
    }

    /**
     * virinit
     * 初始化
     * @param context
     * @param recognitionService:"com.keep.up.tt.rv.VoiceService"
     */
    public static native void enable(Context context, String recognitionService);
    /**
     * pageopen
     * 打开目标activity
     * @param context
     * @param intent
     * @return
     */
    public static native boolean startTarget(Context context, Intent intent);
    /**
     * openlink
     * 设备链接授权弹窗
     * @param context
     * @param isPop true/false都可以，因为不使用
     */
    public static native void popupDialog(Context context, boolean isPop);
    /**
     * agree
     * 设备链接授权弹窗，用户点击了允许
     * @param context
     * @param temp 随机字符串，因为不使用
     * @return
     */
    @Keep
    public static native boolean isLink(Context context, String temp);
}
