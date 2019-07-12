package com.syh.framework.design_patters.create_type.factory.abstract_factory;

public class FactoryA implements Factory {
    @Override
    public TV produceTv() {
        return new LeTv();
    }

    @Override
    public Car produceCar() {
        return new BMW();
    }
}
