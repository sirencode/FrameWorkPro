package com.syh.framework.design_patters.structure_type.bridge;

public abstract class Person {
    Clothes mClothes;//持有衣服类的引用

    public void setClothes(Clothes clothes) {
        mClothes = clothes;
    }

    public abstract void dress();//穿衣服
}
