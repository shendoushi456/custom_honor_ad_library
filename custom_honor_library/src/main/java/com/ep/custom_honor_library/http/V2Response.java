package com.ep.custom_honor_library.http;

/**
 * V2 协议响应模型
 */
public class V2Response {

    private final int code;
    private final String msg;
    private final String dataJson;

    public V2Response(int code, String msg, String dataJson) {
        this.code = code;
        this.msg = msg;
        this.dataJson = dataJson;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDataJson() {
        return dataJson;
    }
}
