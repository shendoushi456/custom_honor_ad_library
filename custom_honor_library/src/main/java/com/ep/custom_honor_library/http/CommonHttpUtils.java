package com.ep.custom_honor_library.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.IGetter;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.OkHttpClient;

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


    public void initDefOaidDoPost(TreeMap<String,Object> params,OnHttpListener onHttpListener){
        initOaidListener(new OaidStatusListener() {
            @Override
            public void oaidSuccess(String oaid) {
                postHttp(params,onHttpListener);
            }
        });
    }


    private boolean isFirstNet = true;
    public void initConfigOaidDoPost(String form, TreeMap<String,Object> params,OnHttpListener onHttpListener){

        initOaidListener(new OaidStatusListener() {
            @Override
            public void oaidSuccess(String oaid) {
                //第一次正常请求
                if (isFirstNet){
                //    Log.d("AD_LOG","第一次 oaidSuccess>>");
                    postConfigHttp(form,params,onHttpListener);
                }
               // Log.d("AD_LOG","判断oaid是否没空>>");
                //判断OAID 是否成功
                if (!TextUtils.isEmpty(oaid) && !isFirstNet){
                //    Log.d("AD_LOG","判断oaid！=null>>");
                    postConfigHttp(form,params,onHttpListener);
                }else if (TextUtils.isEmpty(oaid)){
                //    Log.d("AD_LOG","判断oaid ==null>>");
                    handlerOAID.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initConfigOaidDoPost(form,  params, onHttpListener);
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



    private void postHttp(TreeMap<String,Object> params,OnHttpListener listener){
        JSONObject biz = new JSONObject(params);
        ProtocolV2Client proto = new ProtocolV2Client();
        proto.requestAsync(biz.toString(), new Function1<V2Response, Unit>() {
            @Override
            public Unit invoke(V2Response resp) {
                listener.onSuccess();
                return null;
            }
        }, new Function1<Exception, Unit>() {
            @Override
            public Unit invoke(Exception e) {
                Log.e("AD_LOG", "请求失败", e);
                listener.onFail(new Exception("请求失败"));
                return null;
            }
        });

    }




    private void getConfigNet( String from, OnHttpListener listener){

        String phoneState = PhoneStateUtils.getPhoneState(from);
        JSONObject biz = new JSONObject();
        try {
            biz.put("from", phoneState);
            biz.put("action", "config");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        ProtocolV2Client proto = new ProtocolV2Client();
        proto.requestAsync(biz.toString(), new Function1<V2Response, Unit>() {
            @Override
            public Unit invoke(V2Response resp) {

                Log.i("AD_LOG","resp===="+resp.getMsg());
                if (resp.getCode() != 0){
                    listener.onFail(new Exception("用户失败"+resp.getCode()));
                    Log.i("AD_LOG","用户失败====resp.getCode() "+resp.getCode());
                    return null;
                }

                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(resp.getDataJson());
                    String strategy = jsonObj.getString("strategy");
                    JSONObject strategyObj = new JSONObject(strategy);
                    String keyStr = strategyObj.getString("key");
                    if (keyStr.equals("common")){
                        CommonSpUtils.setUserStatus(true);
                        GsonUtils.toInitConfig(jsonObj.toString(),from);
                        listener.onSuccess();
                    }else{
                        listener.onFail(new Exception("用户失败"));
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        }, new Function1<Exception, Unit>() {
            @Override
            public Unit invoke(Exception e) {
                Log.e("AD_LOG", "请求失败", e);
                listener.onFail(new Exception("请求失败"));

                return null;
            }
        });


    }







    private void postConfigHttp(String from, TreeMap<String,Object> params, OnHttpListener onHttpListener){
        getConfigNet(from, onHttpListener);
    }
































}
