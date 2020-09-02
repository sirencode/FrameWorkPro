package com.syh.framework.http.net_check.model;

import java.util.List;

/**
 * Created by shenyonghe on 2020/9/1.
 */
public class NetCheckBean {
    private String requestUrl;
    private List<String> params;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
