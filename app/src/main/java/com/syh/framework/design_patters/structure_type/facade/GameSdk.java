package com.syh.framework.design_patters.structure_type.facade;

public class GameSdk {
    public void login() {//登录接口
        //调用登录子系统的接口
        LoginManager loginManager = new LoginManager();
        loginManager.login();
    }

    public void pay(int momey) {//支付接口
        //调用支付子系统的接口
        PayManager payManager = new PayManager();
        payManager.pay(momey);
    }
}
