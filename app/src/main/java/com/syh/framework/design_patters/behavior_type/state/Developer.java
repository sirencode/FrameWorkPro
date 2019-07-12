package com.syh.framework.design_patters.behavior_type.state;

public class Developer {
    private DevState devState;

    public void setDevState(DevState devState) {
        this.devState = devState;
    }

    public void develop() {
        setDevState(new AndroidDevState());
        devState.devAPP();
    }

    public void test() {
        setDevState(new TesterState());
        devState.testAPP();
    }

    public void meet() {
        setDevState(new ProductState());
        devState.metting();
    }
}
