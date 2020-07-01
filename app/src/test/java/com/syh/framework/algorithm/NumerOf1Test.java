package com.syh.framework.algorithm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shenyonghe on 2020/5/22.
 */
public class NumerOf1Test {

    @Test
    public void numberOf1() {
        assertEquals(NumerOf1.NumberOf1(2), 1);
        assertEquals(NumerOf1.NumberOf1(0), 0);
        assertEquals(NumerOf1.NumberOf1(-1), 32);
        assertEquals(NumerOf1.NumberOf1(-2), 31);
    }
}