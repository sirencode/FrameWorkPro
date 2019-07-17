package com.syh.framework.design_patters.structure_type.bridge;

public class Student extends Person {

    @Override
    public void dress() {
        System.out.println("学生穿上" + mClothes.getName());
    }
}
