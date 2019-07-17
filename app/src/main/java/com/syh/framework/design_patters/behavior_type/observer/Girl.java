package com.syh.framework.design_patters.behavior_type.observer;

public class Girl implements Observer {

    private String name;

    public Girl(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + ",收到了信息:" + message+"让男朋友去取快递~");
    }
}
