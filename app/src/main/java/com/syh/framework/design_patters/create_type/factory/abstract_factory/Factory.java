package com.syh.framework.design_patters.create_type.factory.abstract_factory;

/**
 * 创建多个产品族中的产品对象。
 */
public interface Factory {
    public TV produceTv();

    public Car produceCar();
}
