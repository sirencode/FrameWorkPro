package com.syh.framework.http.model;

import com.syh.framework.annotions.ChekNull;
import com.syh.framework.annotions.NeedCheck;

public class User implements NeedCheck {
    @ChekNull
    String phoneNum;
    int age;

    @ChekNull
    DataDemo.AAA aaa;

    public DataDemo.AAA getAaa() {
        return aaa;
    }

    public void setAaa(DataDemo.AAA aaa) {
        this.aaa = aaa;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
