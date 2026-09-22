package com.ep.custom_honor_library.http;

public interface OnHttpListener {
    void onSuccess();
    void onFail(Exception e);
}
