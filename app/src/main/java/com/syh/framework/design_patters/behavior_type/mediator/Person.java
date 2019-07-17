package com.syh.framework.design_patters.behavior_type.mediator;

/**
 * 抽象同事类
 */
public abstract class Person {
    protected HouseMediator houseMediator;

    public Person(HouseMediator houseMediator) {
        this.houseMediator = houseMediator;//获取中介
    }

    public abstract void send(String message);//发布信息

    public abstract void getNotice(String message);//接受信息
}
