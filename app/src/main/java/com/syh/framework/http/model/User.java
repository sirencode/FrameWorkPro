package com.syh.framework.http.model;

import com.syh.framework.http.net_check.annotions.CheckNull;
import com.syh.framework.http.net_check.annotions.NeedCheck;

public class User implements NeedCheck {
    @CheckNull
    String phoneNum;
    int age;

    @CheckNull
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
