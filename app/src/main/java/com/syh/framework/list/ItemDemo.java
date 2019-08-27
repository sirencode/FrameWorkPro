package com.syh.framework.list;

public class ItemDemo extends DiffKey {

    private String keyNum;
    private String name;

    public String getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(String keyNum) {
        this.keyNum = keyNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return keyNum;
    }

    // 不同viewholder设置不同的type
    @Override
    int getType() {
        return 0;
    }
}
