package com.syh.framework.design_patters.behavior_type.template;

//
public class ScanBicycle extends DriveInterface {
    @Override
    public void unlock() {
        System.out.println("扫码开锁");
    }

    @Override
    public void ride() {
        System.out.println("骑起来很拉风");
    }

    @Override
    public void lock() {
        System.out.println("上锁");
    }

    @Override
    public void pay() {
        System.out.println("结算");
    }

    @Override
    public void use() {
        unlock();
        ride();
        lock();
        pay();
    }
}

