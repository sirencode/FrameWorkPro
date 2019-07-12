package com.syh.framework.design_patters.behavior_type.state;

public class AndroidDevState implements DevState {
    @Override
    public void testAPP() {

    }

    @Override
    public void devAPP() {
        System.out.println("敲码，codereview，撕逼，改bug");
    }

    @Override
    public void metting() {

    }
}
