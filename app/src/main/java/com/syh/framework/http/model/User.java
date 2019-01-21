package com.syh.framework.http.model;

import com.syh.framework.annotions.ChekValue;

public class User {
    @ChekValue
    String phoneNum;
    int age;

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
