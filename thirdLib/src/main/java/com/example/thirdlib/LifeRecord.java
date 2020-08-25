package com.example.thirdlib;

/**
 * Created by shenyonghe on 2020/8/24.
 */
class LifeRecord {
    public final static int SHOW= 1;
    public final static int HIDE= 2;
    private String name;
    private long time;
    private int type;

    public LifeRecord(String name, long time,int type) {
        this.name = name;
        this.time = time;
        this.type = type;
    }
}
