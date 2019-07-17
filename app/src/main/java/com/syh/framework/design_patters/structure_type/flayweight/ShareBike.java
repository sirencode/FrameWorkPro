package com.syh.framework.design_patters.structure_type.flayweight;

public class ShareBike implements IBike {
    private int price = 1;//单价
    private int total;//总价

    @Override
    public void billing(int time) {
        total = price * time;
        System.out.println("骑车花费了" + total + "元");
    }
}
