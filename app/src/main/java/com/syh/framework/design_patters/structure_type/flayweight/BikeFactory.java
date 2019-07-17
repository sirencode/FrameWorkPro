package com.syh.framework.design_patters.structure_type.flayweight;

import java.util.HashMap;
import java.util.Map;

public class BikeFactory {
    private static Map<String, IBike> pool = new HashMap<>();//使用HashMap来保存IBike对象

    public IBike getBike(String name) {
        IBike bike = null;
        if (pool.containsKey(name)) {//如果存在对象的话，直接使用
            System.out.println("押金已交，直接用车：" + name);
            bike = pool.get(name);
        } else {//对象不存在的话，先创建对象
            bike = new ShareBike();
            pool.put(name, bike);
            System.out.println(name + "交100押金，可以用车了。");
        }
        return bike;
    }
}
