package com.syh.framework;

/**
 * Created by shenyonghe on 2020/4/27.
 */
abstract public class AbClass {
    abstract void run();

    public static void main(String[] args) {
        AbClass abClass = new AbClass() {
            @Override
            void run() {
                System.out.println("run");
            }
        };
        abClass.run();
    }
}
