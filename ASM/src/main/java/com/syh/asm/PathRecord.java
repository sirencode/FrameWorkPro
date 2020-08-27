package com.syh.asm;

/**
 * Created by shenyonghe on 2020/8/25.
 */
public class PathRecord {

    private String type;
    private long time;
    private String name;
    private long maxMemory;
    private long totalMemory;

    public PathRecord(String type, long time, String name, long maxMemory, long totalMemory) {
        this.type = type;
        this.time = time;
        this.name = name;
        this.maxMemory = maxMemory;
        this.totalMemory = totalMemory;
    }

    @Override
    public String toString() {
        return "PathRecord{" +
                "type='" + type + '\'' +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", maxMemory=" + maxMemory +
                ", totalMemory=" + totalMemory +
                '}';
    }
}
