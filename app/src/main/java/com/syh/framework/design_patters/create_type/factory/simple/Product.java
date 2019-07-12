package com.syh.framework.design_patters.create_type.factory.simple;

abstract class Product {
    public void methodSame() {
        System.out.println("same mothod");
    }

    public abstract void methodDiff();
}
