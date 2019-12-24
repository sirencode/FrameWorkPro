package com.syh.framework.algorithm.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shenyonghe on 2019-12-19.
 */
public class StringATest {

    @Test
    public void lengthOfLongestSubstring() {
        assertEquals(StringA.lengthOfLongestSubstring("abcabcbb"),3);
        assertEquals(StringA.lengthOfLongestSubstring("bbbbb"),1);
        assertEquals(StringA.lengthOfLongestSubstring("pwwkew"),3);
        assertEquals(StringA.lengthOfLongestSubstring("dvdf"),3);
    }
}