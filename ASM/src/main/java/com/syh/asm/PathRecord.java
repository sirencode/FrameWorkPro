package com.syh.asm;

import java.text.SimpleDateFormat;

/**
 * Created by shenyonghe on 2020/8/25.
 */
public class PathRecord {

    public String type;
    public long time;
    public String name;
    public String postName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public PathRecord(String type, long time, String name, String postName) {
        this.type = type;
        this.time = time;
        this.name = name;
        this.postName = postName;
    }

    @Override
    public String toString() {
        return "PathRecord{" +
                "type='" + type + '\'' +
                ", time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time) +
                ", name='" + name + '\'' +
                ", postName='" + postName + '\'' +
                '}';
    }
}
