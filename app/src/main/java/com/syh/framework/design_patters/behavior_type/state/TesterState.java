package com.syh.framework.design_patters.behavior_type.state;

public class TesterState implements DevState {
    @Override
    public void testAPP() {
        System.out.println("测试，怼程序员");
    }

    @Override
    public void devAPP() {

    }

    @Override
    public void metting() {
        System.out.println("需求评审移交，产品规划");
    }
}
