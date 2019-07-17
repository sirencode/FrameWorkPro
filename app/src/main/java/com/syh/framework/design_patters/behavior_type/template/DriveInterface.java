package com.syh.framework.design_patters.behavior_type.template;

public abstract class DriveInterface {
    // 开锁
    public abstract void unlock();
    // 骑行
    public abstract void ride();
    // 上锁
    public abstract void lock();
    // 结算
    public abstract void pay();
    // 用户使用
    public abstract void use();
}
