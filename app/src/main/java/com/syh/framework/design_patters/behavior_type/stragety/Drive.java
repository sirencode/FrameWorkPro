package com.syh.framework.design_patters.behavior_type.stragety;

public class Drive {
    private Stragety comStragety;

    public Drive(Stragety comStragety) {
        this.comStragety = comStragety;
    }

    public void doSomething() {
        comStragety.doSomething();
    }
}
