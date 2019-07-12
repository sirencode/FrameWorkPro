package com.syh.framework.design_patters.create_type.factory.method_factory;

/**
 * 让子类来决定要创建哪个对象。
 */
public interface Factory {
    public Product produce();
}
