package com.syh.framework.http.model;

import com.syh.framework.annotions.ChekValue;
import com.syh.framework.annotions.NeedCheck;

import java.util.List;

/**
 * Created bg shenyonghe on 2018/5/22.
 */
public class DataDemo extends NeedCheck {
    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    @ChekValue
    private String shenTest;

    private int anInt;

    private Integer bbb;

    @ChekValue
    private String shenTests[];

    @ChekValue
    private List<String> shenTestss;

    @ChekValue
    private AAA aaa;

    @ChekValue
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public Integer getBbb() {
        return bbb;
    }

    public void setBbb(Integer bbb) {
        this.bbb = bbb;
    }

    public String[] getShenTests() {
        return shenTests;
    }

    public void setShenTests(String[] shenTests) {
        this.shenTests = shenTests;
    }

    public List<String> getShenTestss() {
        return shenTestss;
    }

    public void setShenTestss(List<String> shenTestss) {
        this.shenTestss = shenTestss;
    }

    public AAA getAaa() {
        return aaa;
    }

    public void setAaa(AAA aaa) {
        this.aaa = aaa;
    }

    public String getShenTest() {
        return shenTest;
    }

    public void setShenTest(String shenTest) {
        this.shenTest = shenTest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public static class AAA extends NeedCheck{
        @ChekValue
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
