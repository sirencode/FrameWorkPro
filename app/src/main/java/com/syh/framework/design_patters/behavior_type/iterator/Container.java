package com.syh.framework.design_patters.behavior_type.iterator;

/**
 * 容器角色
 */
public interface Container {
    void add(Object obj);
    void remove(Object obj);
    Iterator createIterator();
}
