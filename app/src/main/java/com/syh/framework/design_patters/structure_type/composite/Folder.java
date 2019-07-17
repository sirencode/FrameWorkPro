package com.syh.framework.design_patters.structure_type.composite;

import java.util.ArrayList;
import java.util.List;

public class Folder extends Component {
    private String name;
    private List<Component> componentList = new ArrayList<Component>();

    public Folder(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void add(Component component) {
        this.componentList.add(component);
    }

    @Override
    public void remove(Component component) {
        this.componentList.remove(component);
    }

    @Override
    public void print() {
        System.out.println(this.getName());
        for (Component component : this.componentList) {
            component.print();
        }
    }
}

