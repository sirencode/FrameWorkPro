package com.syh.framework.design_patters.behavior_type.iterator;

public interface Iterator {
    Object first();
    Object next();
    boolean hasNext();
    Object currentItem();
}
