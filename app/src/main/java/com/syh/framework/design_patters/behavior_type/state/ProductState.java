package com.syh.framework.design_patters.behavior_type.state;

public class ProductState implements DevState {
    @Override
    public void testAPP() {

    }

    @Override
    public void devAPP() {

    }

    @Override
    public void metting() {
        System.out.println("需求评审移交，产品规划");
    }
}
