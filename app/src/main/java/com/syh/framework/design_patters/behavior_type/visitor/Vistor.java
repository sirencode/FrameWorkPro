package com.syh.framework.design_patters.behavior_type.visitor;

/**
 * 抽象访问者
 * 根据访问者不同可以进行不同的访问操作(操作合集中)
 */
public interface Vistor {
    void visit(ConcreteElementNodeA node);
    void visit(ConcreteElementNodeB node);
}
