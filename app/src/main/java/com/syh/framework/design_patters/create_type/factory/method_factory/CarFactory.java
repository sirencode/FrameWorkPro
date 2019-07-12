package com.syh.framework.design_patters.create_type.factory.method_factory;

public class CarFactory implements Factory {
    @Override
    public Product produce() {
        return new Car();
    }
}
