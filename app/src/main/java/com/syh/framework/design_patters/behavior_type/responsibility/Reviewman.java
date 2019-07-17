package com.syh.framework.design_patters.behavior_type.responsibility;

/**
 * 多个对象处理同一请求时，但是具体由哪个对象去处理需要运行时做判断。
 */
public abstract class Reviewman {
    public Reviewman nextReviewman;

    public abstract void review(int days);
}
