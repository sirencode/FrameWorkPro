package com.syh.framework.design_patters.behavior_type.visitor;

public class ConcreteVisitorB implements Vistor {
    @Override
    public void visit(ConcreteElementNodeA node) {
        System.out.println(node.operationA());
    }

    @Override
    public void visit(ConcreteElementNodeB node) {
        System.out.println(node.operationB());
    }
}
