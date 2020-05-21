package com.syh.framework.algorithm;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by shenyonghe on 2020/5/19.
 */
public class SpaceReplaceTest {

    String base = "We Are Happy";
    String replace = "We%20Are%20Happy";

    @Test
    public void replaceSpace() {
        assertEquals(SpaceReplace.replaceSpace(base), replace);
        assertEquals(SpaceReplace.replaceSpace(null), "");
    }

    @Test
    public void replaceSpaceByArray() {
        assertEquals(SpaceReplace.replaceSpaceByArray(base), replace);
        assertEquals(SpaceReplace.replaceSpaceByArray(""), "");
        assertEquals(SpaceReplace.replaceSpaceByArray(null), "");
    }
}