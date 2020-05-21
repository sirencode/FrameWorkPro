package com.syh.framework.algorithm;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by shenyonghe on 2020/5/19.
 */
public class TwoDimenArrayFindTest {

    static int[][] array;

    // 只在所有测试之前执行，只会执行一次。
    @BeforeClass
    public static void init() {
        array = new int[5][4];
        int value = 0;
        for (int i = 0; i < array.length; i++) {
            for (int i1 = 0; i1 < array[i].length; i1++) {
                array[i][i1] = value;
                value++;
            }
            System.out.println("array" + Arrays.toString(array[i]));
        }
    }

    // 在每个测试方法之前运行
    @Before
    public void setUp() throws Exception {

    }

    // 在每个测试方法之前运行
    @After
    public void doSom() {

    }

    @Test
    public void find() {
        assertEquals(TwoDimenArrayFind.find(12, array), true);
        assertEquals(TwoDimenArrayFind.find(100, array), false);
    }

    @Test
    public void leftFind() {
        assertEquals(TwoDimenArrayFind.leftFind(12, array), true);
        assertEquals(TwoDimenArrayFind.leftFind(100, array), false);
    }

    @Test
    public void rightFind() {
        assertEquals(TwoDimenArrayFind.rightFind(12, array), true);
        assertEquals(TwoDimenArrayFind.rightFind(100, array), false);
    }
}