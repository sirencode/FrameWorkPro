package com.syh.framework.design_patters.structure_type.decorator;

public abstract class RoomDecorator extends Room {
    private Room mRoom;//持有被装饰者的引用，这里是需要装修的房间

    public RoomDecorator(Room room) {
        this.mRoom = room;
    }

    @Override
    public void fitment() {
        mRoom.fitment();//调用被装饰者的方法
    }
}
