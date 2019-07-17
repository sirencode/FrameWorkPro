package com.syh.framework.design_patters.behavior_type.visitor;

public class ConcreteElementNodeB extends ElementNode {
    @Override
    public void accept(Vistor vistor) {
        vistor.visit(this);
    }

    public String operationB() {
        return "ConcreteElementNodeB";
    }
}
