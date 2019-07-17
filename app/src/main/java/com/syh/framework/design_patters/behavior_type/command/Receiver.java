package com.syh.framework.design_patters.behavior_type.command;

public class Receiver {
    public void action() {//接收者执行具体的操作
        System.out.println("接收者执行具体的操作");
        System.out.println("开始执行关机操作：");
        System.out.println("退出所有程序进程");
        System.out.println("关机～");
    }
}
