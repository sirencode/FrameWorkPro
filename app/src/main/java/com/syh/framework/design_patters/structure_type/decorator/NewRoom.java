package com.syh.framework.design_patters.structure_type.decorator;

public class NewRoom extends Room {
    @Override
    public void fitment() {
        System.out.println("这是一间新房：装上电");
    }
}
