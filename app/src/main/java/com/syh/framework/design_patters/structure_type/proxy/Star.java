package com.syh.framework.design_patters.structure_type.proxy;

public class Star implements Actor {
    @Override
    public void movie() {
        System.out.println(getClass().getSimpleName() + "：经纪人接了一部电影，我负责拍就好");
    }
}
