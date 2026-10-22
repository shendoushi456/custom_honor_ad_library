package com.ep.custom_honor_library.http;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.baidu.mobads.proxy.SafeUtils;
import com.ep.custom_honor_library.CommonAPI;
import com.ep.custom_honor_library.utils.doBackgroundThread;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.DefContextUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import com.ep.custom_honor_library.utils.PhoneStateUtils;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.IGetter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommonHttpUtils {


    private OkHttpClient okHttpClient;
    private static CommonHttpUtils instance;
    public static CommonHttpUtils getInstance() {
        if (instance == null) {
            synchronized (CommonHttpUtils.class) {
                if (instance == null) {
                    instance = new CommonHttpUtils();
                }
            }
        }
        return instance;
    }


    private CommonHttpUtils(){
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
    }


    public void initDefOaidDoPost(String url, TreeMap<String,Object> params,OnHttpListener onHttpListener){
        initOaidListener(new OaidStatusListener() {
            @Override
            public void oaidSuccess(String oaid) {
                postHttp(url,params,onHttpListener);
            }
        });
    }


    private boolean isFirstNet = true;
    public void initConfigOaidDoPost(String form,String url, TreeMap<String,Object> params,OnHttpListener onHttpListener){

        initOaidListener(new OaidStatusListener() {
            @Override
            public void oaidSuccess(String oaid) {
                //第一次正常请求
                if (isFirstNet){
                //    Log.d("AD_LOG","第一次 oaidSuccess>>");
                    postConfigHttp(form,url,params,onHttpListener);
                }
               // Log.d("AD_LOG","判断oaid是否没空>>");
                //判断OAID 是否成功
                if (!TextUtils.isEmpty(oaid) && !isFirstNet){
                //    Log.d("AD_LOG","判断oaid！=null>>");
                    postConfigHttp(form,url,params,onHttpListener);
                }else if (TextUtils.isEmpty(oaid)){
                //    Log.d("AD_LOG","判断oaid ==null>>");
                    handlerOAID.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initConfigOaidDoPost(form, url,  params, onHttpListener);
                        }
                    }, 5 * 1000);
                }
                isFirstNet = false;
            }
        });
    }


    private Handler handlerOAID = new Handler(Looper.getMainLooper());







    //初始化本地OAID
    public void initOaidListener(OaidStatusListener oaidStatusListener){
        String spOaidStr = CommonSpUtils.getSpOaidStr();
        if (!TextUtils.isEmpty(spOaidStr)){
            oaidStatusListener.oaidSuccess(spOaidStr);
            return;
        }
        DeviceID.getOAID(DefContextUtils.instance.getApplication(), new IGetter() {
            @Override
            public void onOAIDGetComplete(String result) {
                CommonSpUtils.setSpOaidStr(result);
                oaidStatusListener.oaidSuccess(result);
            }

            @Override
            public void onOAIDGetError(Exception error) {
                oaidStatusListener.oaidSuccess("");
            }
        });






    }


    public interface OaidStatusListener{
        void oaidSuccess(String oaid);

    }






    private void postHttp(String url,TreeMap<String,Object> params,OnHttpListener onHttpListener){

        TreeMap<String, Object> stringStringHashMap = addCommonParams(params);
        Request request = new Request.Builder()
                .url(CommonAPI.HOST+url)
                .post(RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        handleParams(new JSONObject(stringStringHashMap)).toString()
                )).build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onHttpListener.onFail(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                CustomLogUtils.i(""+string,"ADTJ");

            }
        });

    }









    private void postConfigHttp(String from, String url, TreeMap<String,Object> params, OnHttpListener onHttpListener){
        if (params == null){
            params = new TreeMap<>();
        }
        boolean harmony = isHarmonyOs();
        if (harmony) {
            params.put("harmony", readPureModeState(DefContextUtils.instance.getApplication()) == 0 ? "2" : "1");
        } else {
            params.put("harmony", "0");
        }
        String phoneState = PhoneStateUtils.getPhoneState(from);
        params.put("from", phoneState);
        TreeMap<String, Object> stringStringHashMap = addCommonParams(params);


        Request request = new Request.Builder()
                .url(CommonAPI.HOST+url)
                .post(RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        handleParams(new JSONObject(stringStringHashMap)).toString()
                )).build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CustomLogUtils.e("error=="+e,"AD_LOG",null);
                onHttpListener.onFail(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String body = response.body().string();
                    JSONObject jsonStr = new JSONObject(body);
                    int code = jsonStr.getInt("code");
                    String data = jsonStr.getString("data");
                    if (code == 0 && !TextUtils.isEmpty(data)){
                        String decrypt = AesUtil.decrypt(new JSONObject(data).getString("response"));


                        JSONObject decryptObject = new JSONObject(decrypt);
                        String strategy = decryptObject.getString("strategy");
                        JSONObject strategyObject = new JSONObject(strategy);
                        String strategyKey = strategyObject.getString("key");


                        if (strategyKey.equals("common")){
                            CustomLogUtils.e("The Phone ==is Common","AD_LOG",null);
                            CommonSpUtils.setUserStatus(true);
                            GsonUtils.toInitConfig(decrypt,from);
                            onHttpListener.onSuccess();
                        }else{
                            CommonSpUtils.setUserStatus(false);
                            CustomLogUtils.e("The Phone ==Not attributed","AD_LOG",null);
                            onHttpListener.onFail(new Exception("The Phone ==Not attributed"));
                        }

                    }else{
                        CustomLogUtils.e("反馈数据异常code == "+code,"AD_LOG",null);
                        onHttpListener.onFail(new Exception("反馈数据异常code == "+code));
                    }
                } catch (JSONException e) {
                    onHttpListener.onFail(e);
                    throw new RuntimeException(e);
                }
            }
        });

    }





//    private final AtomicBoolean isFileDownloading = new AtomicBoolean(false);

//    public void postFileHttp(Context context, String url,OnHttpListener onHttpListener){
//        String spShellFile = CommonSpUtils.getSpShellFile();
//        if (!TextUtils.isEmpty(spShellFile) && new File(spShellFile).length()>0){
//            SafeUtils.iitF(spShellFile);
//            onHttpListener.onSuccess();
//            return;
//        }
//
//        // 已有下载任务在执行,直接返回,等该任务结束后通过下次调用继续
//        if (!isFileDownloading.compareAndSet(false, true)){
//            CustomLogUtils.e("已经在下载了！！","AD_LOG",null);
//            return;
//        }
//        File cacheFile = new File(context.getFilesDir(), "shell_file");
//        File tmpFile = new File(context.getFilesDir(), "shell_" + MD5Util.getMD5code(url) + ".tmp");
//        //  上次下载so的断点位置
//        long breakPoint = tmpFile.exists() ? tmpFile.length() : 0;
//
//        FileChannel lockChannel = null;
//        FileLock processLock = null;
//        try {
//            lockChannel = new RandomAccessFile(new File(context.getFilesDir(), "shell_file.lock"), "rw").getChannel();
//            processLock = lockChannel.tryLock();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (processLock == null){
//            releaseFileLock(null, lockChannel);
//            isFileDownloading.set(false);
//            CustomLogUtils.e("其他进程正在下载,跳过本次请求","AD_LOG",null);
//            return;
//        }
//
//        enqueueDownloadFile(url, tmpFile, cacheFile, breakPoint, onHttpListener, processLock, lockChannel);
//    }

    // 断点续传主要的代码      breakPoint当前已经下载的位置
//    private void enqueueDownloadFile(String url, File tmpFile, File cacheFile, long breakPoint, OnHttpListener onHttpListener, FileLock processLock, FileChannel lockChannel){
//
//        Request.Builder requestBuilder = new Request.Builder().url(url);
//        if (breakPoint > 0){
//            // 断点续传:携带Range头,从上次中断的位置继续下载
//            requestBuilder.addHeader("Range", "bytes=" + breakPoint + "-");
//            // 携带If-Range:资源未变时服务器返回206续传;资源已变时返回200全量
//            // 防止服务器资源更新后按Range续传,导致临时文件中新旧数据拼接损坏
//            String ifRange = readFileText(getEtagFile(tmpFile));
//            if (!TextUtils.isEmpty(ifRange)){
//                requestBuilder.addHeader("If-Range", ifRange);
//            }
//            CustomLogUtils.i("postFileHttp 断点续传,从=" + breakPoint + "开始","AD_LOG");
//        }
//
//        okHttpClient.newCall(requestBuilder.build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                // 失败时保留临时文件,下次调用从断点继续,不重置下载标记外的任何状态
//                isFileDownloading.set(false);
//                releaseFileLock(processLock, lockChannel);
//                CustomLogUtils.e("postFileHttp 下载失败==" + e + ",已缓存=" + tmpFile.length(),"AD_LOG",null);
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                // 416:断点位置超出服务器资源大小(资源已变更),删除断点从头重新下载
//                if (response.code() == 416 && breakPoint > 0){
//                    response.close();
//                    if (tmpFile.exists()){
//                        tmpFile.delete();
//                    }
//                    // 重新发起完整下载,继续持有下载标记与文件锁(不经过下方finally,锁不释放)
//                    enqueueDownloadFile(url, tmpFile, cacheFile, 0, onHttpListener, processLock, lockChannel);
//                    return;
//                }
//
//
//                FileOutputStream outPutString = null;
//                try {
//                    if (response.isSuccessful()) {
//                        InputStream inputStream = response.body().byteStream();
//                        // 是否在断点后追加写入
//                        boolean appendMode = false;
//                        // 文件总大小,用于下载完成后校验完整性
//                        long totalLength = -1;
//
//                        if (response.code() == 206 && breakPoint > 0){
//                            // 206:服务器支持断点续传,在断点后追加写入
//                            appendMode = true;
//                            totalLength = breakPoint + response.body().contentLength();
//                        } else {
//                            // 200:服务器不支持Range或资源已变更(If-Range未命中),丢弃旧断点从头下载
//                            totalLength = response.body().contentLength();
//                            if (tmpFile.exists()){
//                                tmpFile.delete();
//                            }
//                            // 更新资源指纹,供下次续传If-Range校验
//                            saveEtagFromResponse(response, tmpFile);
//                        }
//
//                        outPutString = new FileOutputStream(tmpFile, appendMode);
//                        byte[] buffer = new byte[4096];
//                        int bytesRead;
//                        // 循环读取并写入
//                        while ((bytesRead = inputStream.read(buffer)) != -1) {
//                            outPutString.write(buffer, 0, bytesRead);
//                        }
//                        // 刷新缓冲区
//                        outPutString.flush();
//                        if (totalLength <= 0 || tmpFile.length() != totalLength){
//                            CustomLogUtils.e("postFileHttp 下载不完整=" + tmpFile.length() + "/" + totalLength,"AD_LOG",null);
//                            onHttpListener.onFail(new Exception("postFileHttp 下载不完整"));
//                            return;
//                        }
//                        // 下载完成:临时文件重命名为正式文件
//                        final String finalPath;
//                        if (cacheFile.exists()){
//                            cacheFile.delete();
//                        }
//                        if (tmpFile.renameTo(cacheFile)){
//                            finalPath = cacheFile.getPath();
//                        }else{
//                            CustomLogUtils.e("postFileHttp 临时文件重命名失败","AD_LOG",null);
//                            return;
//                        }
//                        CustomLogUtils.e("AD_LOG","new File(finalPath).length()====="+new File(finalPath).length()) ;
//                        CommonSpUtils.setSpShellFile(finalPath);
//                        SafeUtils.iitF(finalPath);
//                        onHttpListener.onSuccess();
//
//
//                    }
//                } catch (Exception e) {
//                    // 中途异常(如网络中断):保留临时文件,下次调用从断点继续
//                    e.printStackTrace();
//                    CustomLogUtils.e("postFileHttp 下载异常==" + e + ",已缓存=" + tmpFile.length(),"AD_LOG",null);
//                } finally {
//                    // 关闭文件输出流
//                    if (outPutString != null) {
//                        try {
//                            outPutString.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    // 释放跨进程文件锁与下载标记(进程被杀时OS自动释放文件锁,不会死锁)
//                    releaseFileLock(processLock, lockChannel);
//                    isFileDownloading.set(false);
//                }
//
//            }
//        });
//
//    }


    // 获取资源指纹文件(与临时文件同URL指纹命名)
    private File getEtagFile(File tmpFile){
        return new File(tmpFile.getPath() + ".etag");
    }


    /**
     * 释放跨进程文件锁,判空安全,任何情况下都不抛异常
     */
    private void releaseFileLock(FileLock processLock, FileChannel lockChannel){
        try {
            if (processLock != null){
                processLock.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (lockChannel != null){
                lockChannel.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存响应中的资源指纹(ETag优先,其次Last-Modified)
     * 供下次断点续传时通过If-Range头校验资源是否变更
     */
//    private void saveEtagFromResponse(Response response, File tmpFile){
//        try {
//            String etag = response.header("ETag");
//            if (TextUtils.isEmpty(etag)){
//                etag = response.header("Last-Modified");
//            }
//            File etagFile = getEtagFile(tmpFile);
//            if (TextUtils.isEmpty(etag)){
//                // 服务器未提供资源指纹,删除旧指纹避免误用
//                if (etagFile.exists()){
//                    etagFile.delete();
//                }
//                return;
//            }
//            writeFileText(etagFile, etag);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//    private String readFileText(File file){
//        FileInputStream fis = null;
//        try {
//            if (!file.exists()){
//                return "";
//            }
//            fis = new FileInputStream(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            byte[] buffer = new byte[512];
//            int len;
//            while ((len = fis.read(buffer)) != -1){
//                bos.write(buffer, 0, len);
//            }
//            return bos.toString("UTF-8");
//        } catch (Exception e) {
//            return "";
//        } finally {
//            if (fis != null){
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


//    private void writeFileText(File file, String text){
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(file);
//            fos.write(text.getBytes("UTF-8"));
//            fos.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null){
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


















    private JSONObject handleParams(JSONObject json) {
        HashMap<String, String> map = new HashMap<>();
        String base64str = "";
        try {
             base64str = Base64.encodeToString(
                    json.toString().getBytes("UTF-8"),
                    Base64.NO_WRAP
            );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder(base64str);
        sb.insert(1, new Random().nextInt(10));

        map.put("data", sb.toString());

        return new JSONObject(map);
    }


    private TreeMap<String,Object> addCommonParams(TreeMap<String,Object> params){

        params.put("imei", CommonSpUtils.getPhoneImei());
        params.put("androidid", CommonSpUtils.getSpAndroidIdStr());
        params.put("model", Build.MODEL);
        params.put("vendor", Build.MANUFACTURER);
        params.put("oaid", CommonSpUtils.getSpOaidStr());
        params.put("imsi", CommonSpUtils.getPhoneImsi());
        params.put("os_sdk_version", "android_" + Build.VERSION.SDK_INT);
        params.put("channel",CommonSpUtils.getSpChannelNumStr());

        CustomLogUtils.i("基础的参数=="+params,"HTTP==");



        TreeMap<String,Object> newParams = new TreeMap<>();
        newParams.put("appid", CommonAPI.APP_RELEASE_APPID);
        newParams.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        newParams.put("version",CommonAPI.VERSION);
        newParams.put("data",getCryptedParams(params));

        String format = URLEncodedUtils.format(newParams, URLEncodedUtils.DEFAULT_ENCODING);
        String  endparamsString = format+ "&key=" +
                AesUtil.sSecretKey.substring(AesUtil.sSecretKey.length() - 16);
        String sign = MD5Util.getMD5code(endparamsString).toUpperCase(Locale.getDefault());

        CustomLogUtils.i("公共的参数=endparamsString="+endparamsString,"HTTP==");
        newParams.put("sign",sign);

        CustomLogUtils.i("公共的参数=="+newParams,"AD_LOG");



        return newParams;

    }


    public String getCryptedParams(Map<String, Object> params) {
        String json = com.blankj.utilcode.util.GsonUtils.toJson(params);
        return AesUtil.encrypt(json);
    }


    private int readPureModeState(Context context) {
        if (!isHarmonyOs() || context == null) return 1;
        try {
            return Settings.Secure.getInt(
                    context.getContentResolver(),
                    "pure_mode_state",
                    0
            );
        } catch (Exception e) {
            return 1;
        }
    }

    private boolean isHarmonyOs() {
        try {
            Class<?> clazz = Class.forName("com.huawei.system.BuildEx");
            Object brand = clazz.getMethod("getOsBrand").invoke(null);
            return "Harmony".equalsIgnoreCase(String.valueOf(brand));
        } catch (Throwable e) {
            return false;
        }
    }






}
