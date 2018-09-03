package com.syh.framework.http;

/**
 * Created bg shenyonghe on 2018/5/22.
 */
public enum ServerDomainType {
    Home("Home");
    private String name;
    private  ServerDomainType(String name){
        this.name = name;
    }

    public  String getName(){
        return this.name;
    }
}
