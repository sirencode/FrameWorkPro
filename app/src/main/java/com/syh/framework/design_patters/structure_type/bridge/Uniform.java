package com.syh.framework.design_patters.structure_type.bridge;

public class Uniform implements Clothes {

    @Override
    public String getName() {
        return "校服";
    }
}
