package com.syh.framework.design_patters.structure_type.decorator;

public class Bedroom extends RoomDecorator {
    public Bedroom(Room room) {
        super(room);
    }

    @Override
    public void fitment() {
        super.fitment();
        addBedding();
    }

    private void addBedding() {
        System.out.println("装修成卧室：添加卧具");
    }
}
