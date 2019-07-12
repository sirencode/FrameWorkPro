package com.syh.framework.design_patters.create_type.factory.abstract_factory;

public class FactoryB implements Factory {
    @Override
    public TV produceTv() {
        return new SonyTv();
    }

    @Override
    public Car produceCar() {
        return new Audi();
    }
}
