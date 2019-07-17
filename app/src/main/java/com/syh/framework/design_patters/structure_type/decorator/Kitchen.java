package com.syh.framework.design_patters.structure_type.decorator;

public class Kitchen extends RoomDecorator {

    public Kitchen(Room room) {
        super(room);
    }

    @Override
    public void fitment() {
        super.fitment();
        addKitchenware();
    }

    private void addKitchenware() {
        System.out.println("装修成厨房：添加厨具");
    }
}
