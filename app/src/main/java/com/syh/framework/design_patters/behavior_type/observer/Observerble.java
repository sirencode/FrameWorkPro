package com.syh.framework.design_patters.behavior_type.observer;

/**
 * 抽象被观察者
 */
public interface Observerble {
    void add(Observer observer);//添加观察者

    void remove(Observer observer);//删除观察者

    void notify(String message);//通知观察者
}
