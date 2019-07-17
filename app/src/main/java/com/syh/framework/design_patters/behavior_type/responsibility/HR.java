package com.syh.framework.design_patters.behavior_type.responsibility;

public class HR extends Reviewman {

    @Override
    public void review(int days) {
        if (days < 3) {
            System.out.println("HR 审批");
        }
    }
}
