package com.syh.framework.http;


import com.syh.framework.BuildConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created bg shenyonghe on 2018/5/22.
 */
public class DomainUtil {
    private final static Map<ServerDomainType, String> PRODUCT = new HashMap<ServerDomainType, String>() {
        {
            put(ServerDomainType.Home, "https://www.kuaidi100.com/");
        }
    };

    private final static Map<ServerDomainType, String> TEST = new HashMap<ServerDomainType, String>() {
        {
            put(ServerDomainType.Home, "https://www.kuaidi100.com/");
        }
    };

    public static Map<ServerDomainType, String> getDomain() {
        if (BuildConfig.DEBUG) {
            return TEST;
        } else {
            return PRODUCT;
        }
    }
}
