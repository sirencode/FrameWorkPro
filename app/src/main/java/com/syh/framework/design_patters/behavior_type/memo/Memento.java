package com.syh.framework.design_patters.behavior_type.memo;

/**
 * 备忘录类 需要保存对象的某一时刻的状态时
 */
public class Memento {
    public int level;//等级
    public int coin;//金币数量

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getLevel() {
        return level;
    }

    public int getCoin() {
        return coin;
    }
}
