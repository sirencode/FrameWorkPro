package com.syh.framework.http.net_check.gson_adapter.test;

/**
 * Author: shenyonghe
 * Date: 2020/10/26
 * Version: v1.0
 * Description:
 * Modification History:
 * Date Author Version Description
 * ------------------------------------
 * 2020/10/26 shenyonghe v1.0
 * Why & What is modified:
 **/
class UserBean {
    String name;
    String gender;
    String email;
    String address;
    int age;
    boolean hasMarry;
    float weight;
    double height;

    public UserBean(String name, String gender, String email, String address, int age, boolean hasMarry, float weight, double height) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.age = age;
        this.hasMarry = hasMarry;
        this.weight = weight;
        this.height = height;
    }

    @Override
    public String toString() {
        return "name：" + name + "，gender：" + gender + "，email：" + email + ",address：" + address + "，age：" + age + ",hasMarry：" + hasMarry + "，weight：" + weight + ",height：" + height;
    }

}
