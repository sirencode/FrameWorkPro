package com.syh.framework.design_patters.behavior_type;

import com.syh.framework.design_patters.behavior_type.state.Developer;
import com.syh.framework.design_patters.behavior_type.stragety.RedStragety;
import com.syh.framework.design_patters.behavior_type.stragety.Drive;
import com.syh.framework.design_patters.behavior_type.stragety.GreenStragety;

public class BehaviorTest {
    public static void main(String[] args) {
        Drive it;
        System.out.println("绿灯:");
        it = new Drive(new GreenStragety());
        it.doSomething();
        System.out.println("红灯:");
        it = new Drive(new RedStragety());
        it.doSomething();

        Developer developer = new Developer();
        developer.develop();
        developer.meet();
        developer.test();
    }
}
