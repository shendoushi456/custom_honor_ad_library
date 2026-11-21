package com.ep.custom_honor_library.http;

import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.ep.custom_honor_library.CommonAPI;
import com.ep.custom_honor_library.utils.CommonSpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * V2 协议客户端（由 Kotlin 版 ProtocolV2Client 转换而来）
 *
 * 请求流程：业务 JSON -> 补充公共参数 -> AES-256-GCM 加密 -> 组装签名参数
 * 响应流程：JSON 解析 -> HMAC 验签 -> AES-GCM 解密 -> V2Response
 */
public class ProtocolV2Client {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");

    private final byte[] encKey;
    private final byte[] macKey;
    private final String endpoint;

    private final String baseUrl;
    private final String clientId;
    private final String clientSecret;
    private final OkHttpClient okHttpClient;

    /** 请求回调接口（对应 Kotlin 版的 onResult / onError 两个 lambda） */
    public interface V2Callback {
        void onResult(V2Response response);

        void onError(Exception e);
    }

    public ProtocolV2Client() {
        this(CommonAPI.HOST, CommonAPI.APP_RELEASE_APPID, CommonAPI.RELEASE_SSK,
                new OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .build());
    }

    public ProtocolV2Client(String baseUrl, String clientId, String clientSecret, OkHttpClient okHttpClient) {
        this.baseUrl = baseUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.okHttpClient = okHttpClient;

        // 由 clientSecret 派生加解密密钥与验签密钥
        byte[][] keys = CryptoKeys.derive(clientSecret);
        this.encKey = keys[0]; // AES-256-GCM 密钥
        this.macKey = keys[1]; // HMAC-SHA256 密钥
        this.endpoint = trimEndSlash(baseUrl) + "/r";
    }

    // ------------------------- 请求构造 -------------------------

    public String buildRequest(String bizJson) throws JSONException {
        JSONObject json = new JSONObject(bizJson);
        json.put("version", CommonAPI.VERSION);
        json.put("model", Build.MODEL);
        json.put("vendor", Build.MANUFACTURER);
        json.put("os_sdk_version", "android_" + Build.VERSION.SDK_INT);
        json.put("oaid", CommonSpUtils.getSpOaidStr());
        json.put("channel", "9");
        json.put("androidid", CommonSpUtils.getSpAndroidIdStr());
        String jsonStr = json.toString();

        String payload = AesGcm.encrypt(jsonStr.getBytes(StandardCharsets.UTF_8), encKey);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000); // ⚠️ 秒级，不是毫秒
        String nonce = newNonce();

        Map<String, String> signParams = new HashMap<>();
        signParams.put("appid", clientId);
        signParams.put("timestamp", timestamp);
        signParams.put("nonce", nonce);
        signParams.put("payload", payload);
        String sign = Hmac.sign(signParams, macKey);

        JSONObject result = new JSONObject();
        result.put("appid", clientId);
        result.put("timestamp", timestamp);
        result.put("nonce", nonce);
        result.put("payload", payload);
        result.put("sign", sign);
        return result.toString();
    }

    private static String newNonce() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format(Locale.ROOT, "%02x", b));
        }
        return sb.toString();
    }

    public V2Response parseResponse(String respJson) throws JSONException {
        Log.i("AD_LOG", "+respJson===" + respJson);
        JSONObject obj = new JSONObject(respJson);
        int code = obj.optInt("code", -1);
        String msg = obj.optString("msg", "");

        if (!obj.has("payload")) {
            return new V2Response(code, msg, null);
        }

        Map<String, String> verifyParams = new HashMap<>();
        verifyParams.put("code", obj.get("code").toString());
        verifyParams.put("msg", obj.optString("msg", ""));
        verifyParams.put("nonce", obj.optString("nonce", ""));
        verifyParams.put("payload", obj.optString("payload", ""));
        String sign = obj.optString("sign", "");
        if (!Hmac.verify(verifyParams, macKey, sign)) {
            throw new SecurityException("响应验签失败");
        }

        byte[] plain = AesGcm.decrypt(obj.optString("payload", ""), encKey);
        String plainStr = new String(plain, StandardCharsets.UTF_8);
        Log.i("AD_LOG", "String(plain, Charsets.UTF_8)===" + plainStr);

        return new V2Response(code, msg, plainStr);
    }

    // ------------------------- 异步请求 -------------------------

    public void requestAsync(final String bizJson, final V2Callback callback) {
        final String body;
        try {
            body = buildRequest(bizJson);
        } catch (Exception e) {
            callback.onError(e);
            return;
        }

        Request req = new Request.Builder()
                .url(endpoint)
                .post(RequestBody.create(body, JSON_MEDIA_TYPE))
                .build();

        okHttpClient.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    ResponseBody respBody = response.body();
                    if (respBody == null) {
                        throw new IOException("空响应体");
                    }
                    callback.onResult(parseResponse(respBody.string()));
                } catch (Exception e) {
                    callback.onError(e);
                } finally {
                    response.close();
                }
            }
        });
    }

    /** 去除字符串末尾所有 '/'（等价 Kotlin 的 trimEnd('/')） */
    private static String trimEndSlash(String s) {
        int end = s.length();
        while (end > 0 && s.charAt(end - 1) == '/') {
            end--;
        }
        return s.substring(0, end);
    }

    /** 拼接多个 byte 数组 */
    private static byte[] concat(byte[]... arrays) {
        int total = 0;
        for (byte[] a : arrays) {
            total += a.length;
        }
        byte[] result = new byte[total];
        int pos = 0;
        for (byte[] a : arrays) {
            System.arraycopy(a, 0, result, pos, a.length);
            pos += a.length;
        }
        return result;
    }

    // ------------------------- 工具类（对应 Kotlin 的 object） -------------------------

    /** HKDF-SHA256 密钥派生 */
    static final class Hkdf {
        private Hkdf() {
        }

        static byte[] sha256(byte[] ikm, byte[] salt, byte[] info, int length) {
            try {
                Mac mac = Mac.getInstance("HmacSHA256");
                // Extract: PRK = HMAC(salt, IKM)
                mac.init(new SecretKeySpec(salt, "HmacSHA256"));
                byte[] prk = mac.doFinal(ikm);
                // Expand: T(n) = HMAC(PRK, T(n-1) + info + n)
                mac.init(new SecretKeySpec(prk, "HmacSHA256"));
                byte[] okm = new byte[length];
                byte[] t = new byte[0];
                int pos = 0;
                int counter = 1;
                while (pos < length) {
                    mac.reset();
                    mac.update(t);
                    mac.update(info);
                    mac.update((byte) counter);
                    t = mac.doFinal();
                    int n = Math.min(t.length, length - pos);
                    System.arraycopy(t, 0, okm, pos, n);
                    pos += n;
                    counter++;
                }
                return okm;
            } catch (GeneralSecurityException e) {
                // 与 Kotlin 版语义一致：算法本身保证存在，异常以非受检形式向上传播
                throw new RuntimeException(e);
            }
        }
    }

    /** 加解密密钥与验签密钥派生 */
    static final class CryptoKeys {
        private static final String SALT = "t-app-v2";

        private CryptoKeys() {
        }

        /** 返回 [encKey, macKey] */
        static byte[][] derive(String clientSecret) {
            byte[] ikm = hexToBytes(clientSecret);
            byte[] salt = SALT.getBytes(StandardCharsets.UTF_8);
            byte[] encKey = Hkdf.sha256(ikm, salt, "enc".getBytes(StandardCharsets.UTF_8), 32); // AES-256-GCM 密钥
            byte[] macKey = Hkdf.sha256(ikm, salt, "mac".getBytes(StandardCharsets.UTF_8), 32); // HMAC-SHA256 密钥
            return new byte[][]{encKey, macKey};
        }

        static byte[] hexToBytes(String hex) {
            int len = hex.length();
            byte[] out = new byte[len / 2];
            int i = 0;
            while (i < len) {
                out[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                        + Character.digit(hex.charAt(i + 1), 16));
                i += 2;
            }
            return out;
        }
    }

    /** AES-256-GCM 加解密（IV + TAG + CIPHER 的组合顺序与服务端约定一致） */
    static final class AesGcm {
        private static final int IV_LEN = 12;
        private static final int TAG_LEN = 16;

        private AesGcm() {
        }

        static String encrypt(byte[] plain, byte[] key) {
            try {
                byte[] iv = new byte[IV_LEN];
                new SecureRandom().nextBytes(iv);
                Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"),
                        new GCMParameterSpec(TAG_LEN * 8, iv));
                byte[] out = cipher.doFinal(plain); // = cipher + tag
                byte[] cipherPart = Arrays.copyOfRange(out, 0, out.length - TAG_LEN);
                byte[] tag = Arrays.copyOfRange(out, out.length - TAG_LEN, out.length);
                byte[] combined = concat(iv, tag, cipherPart); // 与服务端约定一致
                return Base64.encodeToString(combined, Base64.NO_WRAP);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }

        static byte[] decrypt(String base64Data, byte[] key) {
            try {
                byte[] raw = Base64.decode(base64Data, Base64.NO_WRAP);
                if (raw.length <= IV_LEN + TAG_LEN) {
                    throw new IllegalArgumentException("cipher too short");
                }
                byte[] iv = Arrays.copyOfRange(raw, 0, IV_LEN);
                byte[] tag = Arrays.copyOfRange(raw, IV_LEN, IV_LEN + TAG_LEN);
                byte[] cipherPart = Arrays.copyOfRange(raw, IV_LEN + TAG_LEN, raw.length);
                byte[] input = concat(cipherPart, tag); // Android 解密需要 cipher+tag
                Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"),
                        new GCMParameterSpec(TAG_LEN * 8, iv));
                return cipher.doFinal(input); // 验签失败/密文被篡改会抛 AEADBadTagException
            } catch (GeneralSecurityException e) {
                // AEADBadTagException 等以非受检形式传播，最终进入 requestAsync 的 onError
                throw new RuntimeException(e);
            }
        }
    }

    /** HMAC-SHA256 参数签名与验签 */
    static final class Hmac {
        private Hmac() {
        }

        static String sign(Map<String, String> param, byte[] macKey) {
            // 过滤掉 sign 本身并按 key 排序后拼接：k1=v1&k2=v2...
            TreeMap<String, String> sorted = new TreeMap<>();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                if (!"sign".equals(entry.getKey())) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
            }
            StringBuilder raw = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : sorted.entrySet()) {
                if (!first) {
                    raw.append('&');
                }
                raw.append(entry.getKey()).append('=').append(entry.getValue());
                first = false;
            }
            try {
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(new SecretKeySpec(macKey, "HmacSHA256"));
                byte[] out = mac.doFinal(raw.toString().getBytes(StandardCharsets.UTF_8));
                StringBuilder hex = new StringBuilder();
                for (byte b : out) {
                    hex.append(String.format(Locale.ROOT, "%02x", b));
                }
                return hex.toString();
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }

        static boolean verify(Map<String, String> param, byte[] macKey, String sign) {
            String expect = sign(param, macKey);
            return MessageDigest.isEqual(
                    expect.getBytes(StandardCharsets.UTF_8),
                    sign.toLowerCase(Locale.ROOT).getBytes(StandardCharsets.UTF_8));
        }
    }
}
