package com.syh.framework.design_patters.structure_type.bridge;

public class Shirt implements Clothes {

    @Override
    public String getName() {
        return "衬衫";
    }
}
