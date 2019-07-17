package com.syh.framework.design_patters.behavior_type.visitor;

/**
 * 具体被访问元素
 */
public class ConcreteElementNodeA extends ElementNode {
    @Override
    public void accept(Vistor vistor) {
        vistor.visit(this);
    }

    public String operationA() {
        return "ConcreteElementNodeA";
    }
}
