package com.syh.framework.design_patters.behavior_type.stragety;

/**
 * 封装不同的算法，算法之间能互相替换。
 * ListView时都需要设置一个Adapter，而这个Adapter根据我们实际的需求可以用ArrayAdapter、SimpleAdapter等等，这里就运用到策略模式。
 */
public interface Stragety {
    void doSomething();
}
