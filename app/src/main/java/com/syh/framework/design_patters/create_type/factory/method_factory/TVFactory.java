package com.syh.framework.design_patters.create_type.factory.method_factory;

public class TVFactory implements Factory {
    @Override
    public Product produce() {
        return new TV();
    }
}
