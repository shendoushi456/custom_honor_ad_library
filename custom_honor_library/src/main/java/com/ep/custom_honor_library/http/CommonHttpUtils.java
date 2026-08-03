package com.ep.custom_honor_library.http;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;

import com.lx.c_interface_library.CommonAPI;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.DefContextUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import com.ep.custom_honor_library.utils.PhoneStateUtils;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.IGetter;
import com.lx.c_interface_library.OnHttpListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

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


    public void initConfigOaidDoPost(String form,String url, TreeMap<String,Object> params,OnHttpListener onHttpListener){
        initOaidListener(new OaidStatusListener() {
            @Override
            public void oaidSuccess(String oaid) {
                postConfigHttp(form,url,params,onHttpListener);
            }
        });
    }



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
                CommonSpUtils.setSpOaidStr("");
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
        params.put("board", Build.BOARD);
        params.put("oaid", CommonSpUtils.getSpOaidStr());
        params.put("imsi", CommonSpUtils.getPhoneImsi());
        params.put("sdk", "android_" + Build.VERSION.SDK_INT);
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

//        CustomLogUtils.i("公共的参数=="+newParams,"AD_LOG");



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
