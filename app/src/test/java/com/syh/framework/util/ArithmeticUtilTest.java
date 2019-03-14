package com.syh.framework.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * create by shenyonghe on 2019/3/14
 **/
public class ArithmeticUtilTest {

    @Test
    public void round() {
        assertEquals(ArithmeticUtil.round(1.16123123,2) == 1.16d, true);
        assertEquals(ArithmeticUtil.round(1.16163123,3) == 1.162d, true);
    }

    @Test
    public void sub() {
        assertEquals(ArithmeticUtil.sub(1.0,0.9) == 0.1, true);
        assertEquals(ArithmeticUtil.sub(1.0,0.42) == 0.58, true);
        assertEquals(1.0d - 0.9d == 0.1, false);
        assertEquals(1.0d - 0.42 == 0.58, false);
        System.out.println(1.0d - 0.42);
    }

    @Test
    public void add() {
        assertEquals(ArithmeticUtil.add(0.05,0.01) == 0.06, true);
        assertEquals(0.05 + 0.01 == 0.06, false);
        System.out.println(0.05 + 0.01);
    }

    @Test
    public void mul() {
        assertEquals(ArithmeticUtil.mul(4.015,100) == 401.5, true);
        assertEquals(4.015*100 == 401.5, false);
        System.out.println(4.015*100);
    }

    @Test
    public void div() {
        assertEquals(ArithmeticUtil.div(123.3,100) == 1.233, true);
        assertEquals(123.3/100 == 1.233, false);
        System.out.println(123.3/100);
    }
}