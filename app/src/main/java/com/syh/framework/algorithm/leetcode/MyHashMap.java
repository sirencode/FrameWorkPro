package com.syh.framework.algorithm.leetcode;

/**
 * Created by shenyonghe on 2019-12-06.
 * 设计哈希映射
 * 不使用任何内建的哈希表库设计一个哈希映射
 * <p>
 * 说明
 * put(key, value)：向哈希映射中插入(键,值)的数值对。如果键对应的值已经存在，更新这个值。
 * get(key)：返回给定的键所对应的值，如果映射中不包含这个键，返回-1。
 * remove(key)：如果映射中存在这个键，删除这个数值对。
 * <p>
 * 注意
 * 所有的值都在 [1, 1000000]的范围内。
 * 操作的总数目在[1, 10000]范围内。
 * 不要使用内建的哈希库。
 */
public class MyHashMap {

    int[] map;

    /**
     * Initialize your data structure here.
     */
    public MyHashMap() {
        map = new int[1000001];
        for(int i = 0;i < map.length;i++){
            map[i] = -1;
        }
    }

    /**
     * value will always be non-negative.
     */
    public void put(int key, int value) {
        map[key] = value;
    }

    /**
     * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
     */
    public int get(int key) {
        return map[key];
    }

    /**
     * Removes the mapping of the specified value key if this map contains a mapping for the key
     */
    public void remove(int key) {
        map[key] = -1;
    }

    public static void main(String[] args) {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put(1, 1);
        hashMap.put(2, 2);
        System.out.println(hashMap.get(1)); // 返回 1
        System.out.println(hashMap.get(3)); // 返回 -1 (未找到)
        hashMap.put(2, 1); // 更新已有的值
        System.out.println(hashMap.get(2)); // 返回 1
        hashMap.remove(2); // 删除键为2的数据
        System.out.println(hashMap.get(2)); // 返回 -1 (未找到)
    }
}
