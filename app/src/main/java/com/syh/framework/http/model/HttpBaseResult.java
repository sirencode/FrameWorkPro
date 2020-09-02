package com.syh.framework.http.model;

import com.syh.framework.http.net_check.annotions.NeedCheck;

public class HttpBaseResult<T> implements NeedCheck {
    private String requesrt;
    private T data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequest() {
        return requesrt;
    }

    public void setRequest(String url) {
        this.requesrt = url;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
