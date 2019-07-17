package com.syh.framework.design_patters.behavior_type.visitor;

/**
 * 抽象元素
 */
public abstract class ElementNode {
    public abstract void accept(Vistor vistor);
}
