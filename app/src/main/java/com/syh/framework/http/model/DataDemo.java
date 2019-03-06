package com.syh.framework.http.model;

import com.syh.framework.annotions.ChekNull;
import com.syh.framework.annotions.NeedCheck;

import java.util.List;

/**
 * Created bg shenyonghe on 2018/5/22.
 */
public class DataDemo implements NeedCheck {
    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    @ChekNull
    private String shenTest;

    private int anInt;

    private Integer bbb = new Integer(100000);

//    @ChekNull
    private String shenTests[] = {"1","2"};

    private String sets[] = {"1","2"};

//    @ChekNull
    private List<String> shenTestss;

    @ChekNull
    private List<User> users;

    @ChekNull
    private AAA aaa;

//    @ChekNull
    private User user;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

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

    public static class AAA implements NeedCheck{
        @ChekNull
        String name;

        @ChekNull
        String age;

        @ChekNull
        User users;

        public User getUsers() {
            return users;
        }

        public void setUsers(User users) {
            this.users = users;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
