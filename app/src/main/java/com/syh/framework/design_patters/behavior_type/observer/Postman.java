package com.syh.framework.design_patters.behavior_type.observer;

import java.util.ArrayList;
import java.util.List;

public class Postman implements Observerble {

    private List<Observer> personList = new ArrayList<Observer>();//保存收件人（观察者）的信息

    @Override
    public void add(Observer observer) {
        personList.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        personList.remove(observer);
    }

    @Override
    public void notify(String message) {
        if (personList.size() > 0) {
            for (Observer observer : personList) {
                observer.update(message);
            }
        }
    }
}
