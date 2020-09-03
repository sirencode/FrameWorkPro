package com.syh.framework.http.net_check.model;

import java.util.List;

/**
 * Created by shenyonghe on 2020/9/1.
 * url : http://www.95fenapp.com/api_goods/skuDetail/v1.0?goods_id=YKp8k2NKJ&sn=HomeThreeCList&timestamp=1599037009824&token=1bc6e18a4ef672acc78c8237b6d7c716
 * {
 *     "status":0,
 *     "msg":"ok",
 *     "data":{
 *         "checkData":[
 *             {
 *                 "requestUrl":"api_goods/skuDetail/v1.0",
 *                 "params":[
 *                    "id",
 *                    "img_attr_detail.img",
 *                    "size_desc",
 *                    "share_body.title"
 *                 ]
 *             }
 *         ]
 *     }
 * }
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
