package com.syh.framework.design_patters.behavior_type.responsibility;

public class TeamLeader extends Reviewman {

    @Override
    public void review(int days) {
        if (days >= 3) {
            System.out.println("Teamleader 审批");
        } else {
            if (nextReviewman != null) {
                nextReviewman.review(days);
            } else {
                System.out.println("未找到下一级审批人");
            }
        }
    }
}
