package com.syh.framework.design_patters.structure_type.bridge;

public class Coder extends Person {

    @Override
    public void dress() {
        System.out.println("程序员穿上" + mClothes.getName());
    }
}
