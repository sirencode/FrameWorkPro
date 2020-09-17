package com.syh.asm;

/**
 * Created by shenyonghe on 2020/8/25.
 */
public class PathRecord {

    private String type;
    private long time;
    private String name;

    public PathRecord(String type, long time, String name) {
        this.type = type;
        this.time = time;
        this.name = name;
    }

    @Override
    public String toString() {
        return "PathRecord{" +
                "type='" + type + '\'' +
                ", time=" + time +
                ", name='" + name + '\'' +
                '}';
    }
}
