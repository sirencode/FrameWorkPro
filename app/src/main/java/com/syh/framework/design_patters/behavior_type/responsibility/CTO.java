package com.syh.framework.design_patters.behavior_type.responsibility;

public class CTO extends Reviewman {

    @Override
    public void review(int days) {
        if (days >= 7) {
            System.out.println("CTO 审批");
        } else {
            if (nextReviewman != null) {
                nextReviewman.review(days);
            } else {
                System.out.println("未找到下一级审批人");
            }
        }
    }
}
