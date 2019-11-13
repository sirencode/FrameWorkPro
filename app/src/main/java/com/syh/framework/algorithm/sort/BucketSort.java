package com.syh.framework.algorithm.sort;

import java.util.Arrays;

/**
 * Created by shenyonghe on 2019-11-02.
 * 对6个人的英语测试成绩（1~10分）进行排序。假如分数是[6,5,8,8,10,9],用桶排序的思想就是准备10个桶，编号依次为1~10，
 * 将成绩放入对应的桶中，例如6分放入6号桶，两个8分放入8号桶...然后按照桶的标号顺序逐一输出（有就输出，没有就不输出），这就是桶排序的基本思想。
 *
 * 事实上，这只是一个简易版，试想一下，如果待排序的元素跨度范围比较大，例如1~10000，是不是需要10000个桶？
 * 实际上这种情况下，一个桶里并非总放一个元素，很多时候一个桶里放多个元素。其实真正的桶排序和散列表有一样的原理。
 *
 * 实际排序中，通常对每个桶中的元素继续使用其他排序算法进行排序，所以更多时候，桶排序会结合其他排序算法一起使用。
 */
public class BucketSort {
    private int[] buckets;
    private int[] array;

    public BucketSort(int range, int[] array) {
        this.buckets = new int[range];
        this.array = array;
    }

    /**
     *
     */
    public void sort() {
        if (array != null && array.length > 1) {
            for (int i = 0; i < array.length; i++) {
                buckets[array[i]]++;
            }
        }
    }

    /*排序输出*/
    public void sortOut() {
        //倒序输出数据
        for (int i = buckets.length - 1; i >= 0; i--) {
            for (int j = 0; j < buckets[i]; j++) {
                System.out.print(i + "\t");
            }
        }
    }

    /*排序输出*/
    public void sortOutUp() {
        //倒序输出数据
        for (int i = 0; i < buckets.length - 1; i++) {
            for (int j = 0; j < buckets[i]; j++) {
                System.out.print(i + "\t");
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {5, 7, 3, 5, 4, 8, 6, 4, 1, 2};
        BucketSort bs = new BucketSort(10, array);
        bs.sort();
        bs.sortOut();
        bs.sortOutUp();
    }
}
