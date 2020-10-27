package com.syh.framework.http;

import com.syh.framework.http.gson.BaseGsonConverterFactory;
import com.syh.framework.http.net_check.DataCheckInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiFactory {
    private volatile static ApiFactory instance;

    public static ApiFactory getInstance() {
        if (instance == null) {
            synchronized (ApiFactory.class) {
                if (instance == null) {
                    instance = new ApiFactory();
                }
            }
        }
        return instance;
    }

    private Map<String, Object> apis;
    private Map<String, Retrofit> retrofitMap;
    private Interceptor[] interceptors;
    private Map<ServerDomainType, Interceptor[]> interceptorMap;
    private Converter.Factory converterFactory = GsonConverterFactory.create();
    private boolean enableLog = false;

    private ApiFactory() {
        apis = new HashMap<>();
        retrofitMap = new HashMap<>();
    }

    public void clearCache() {
        apis.clear();
    }

    public void addConverterFactory(Converter.Factory factory) {
        converterFactory = factory;
    }

    public void setInterceptors(Interceptor[] interceptors) {
        this.interceptors = interceptors;
    }

    public void setInterceptors(ServerDomainType domainType, Interceptor[] interceptors) {
        if (interceptors == null || interceptors.length == 0) {
            return;
        }
        if (interceptorMap == null) {
            interceptorMap = new HashMap<>();
        }
        interceptorMap.put(domainType, interceptors);
    }

    public void setEnableLog(boolean enableLog) {
        this.enableLog = enableLog;
    }

    public <T> T create(ServerDomainType serverDomainType, Class<T> tClass) {
        String key = getKey(serverDomainType, tClass);
        T api = (T) apis.get(key);
        if (api == null) {
//
//            Retrofit retrofit = new RetrofitBuilder()
//                    .withDomain(DomainUtil.getDomain().get(serverDomainType))
//                    .withDebug(enableLog)
//                    .withInterceptors(getDefaultInterceptors())
//                    .withConvertFactory(BaseGsonConverterFactory.create())
//                    .build();
            Retrofit retrofit = retrofitMap.get(serverDomainType.getName());
            if (retrofit == null) {
                retrofit = new RetrofitBuilder()
                        .withDomain(DomainUtil.getDomain().get(serverDomainType))
                        .withDebug(enableLog)
                        .withInterceptors(getDefaultInterceptors())
                        .withConvertFactory(BaseGsonConverterFactory.create())
                        .build();
                retrofitMap.put(serverDomainType.getName(), retrofit);
            }

            api = retrofit.create(tClass);
            apis.put(key, api);
        }
        return api;
    }

    private List<Interceptor> getDefaultInterceptors() {
        List<Interceptor> list = new ArrayList<>();
        list.add(new LogInterceptor());
        list.add(new DataCheckInterceptor());
        return list;
    }

    private static final String KEY_TEMPLATE = "%s_%s";

    private String getKey(ServerDomainType serverDomainType, Class tClass) {
        return String.format(KEY_TEMPLATE, serverDomainType.name(), tClass.getSimpleName());
    }

    private List<Interceptor> getInterceptors(ServerDomainType serverDomainType) {
        List<Interceptor> res = new ArrayList<>();
        if (interceptors != null && interceptors.length != 0) {
            res.addAll(Arrays.asList(interceptors));
        }
        if (interceptorMap != null) {
            Interceptor[] interceptors = interceptorMap.get(serverDomainType);
            if (interceptors != null && interceptors.length != 0) {
                res.addAll(Arrays.asList(interceptors));
            }
        }
        return res;
    }

//    public <T> T create(ServerDomainType serverDomainType, Class<T> tClass) {
//        return create(serverDomainType, tClass, converterFactory);
//    }
}