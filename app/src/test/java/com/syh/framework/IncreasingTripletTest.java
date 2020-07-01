package com.syh.framework;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by shenyonghe on 2020/6/10.
 */
public class IncreasingTripletTest {

    @Test
    public void test() {
        int[] a = new int[]{1, 2, 3, 4, 5};
        int[] b = new int[]{5, 4, 3, 2, 1};
        int[] c = new int[]{1, 5, 2, 5, 1};
        assertEquals(IncreasingTriplet.increasingTriplet(a), true);
        assertEquals(IncreasingTriplet.increasingTriplet(b), false);
        assertEquals(IncreasingTriplet.increasingTriplet(c), true);
    }
}