package com.syh.framework.design_patters.behavior_type.visitor;

import java.util.ArrayList;
import java.util.List;

public class ObjectStructure {
    private List<ElementNode> nodeList = new ArrayList<>();

    public void action(Vistor vistor) {
        for (ElementNode node : nodeList) {
            node.accept(vistor);
        }
    }

    public void add(ElementNode node) {
        nodeList.add(node);
    }
}
