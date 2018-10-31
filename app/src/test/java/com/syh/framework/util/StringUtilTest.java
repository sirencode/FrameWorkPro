package com.syh.framework.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void isChineseCharacter() {
        assertEquals(StringUtil.isChineseCharacter(','), false);
        assertEquals(StringUtil.isChineseCharacter('，'), true);
    }

    @Test
    public void isChineseWord() {
        assertEquals(StringUtil.isChineseCharacter('c'), false);
        assertEquals(StringUtil.isChineseCharacter('申'), true);
    }

    @Test
    public void getCharLength() {
        assertEquals(StringUtil.getCharLength('c'), 1);
        assertEquals(StringUtil.getCharLength('申'), 2);
    }

    @Test
    public void getStringLength() {
        assertEquals(StringUtil.getStringLength(null), 0);
        assertEquals(StringUtil.getStringLength(""), 0);
        assertEquals(StringUtil.getStringLength("申"), 2);
        assertEquals(StringUtil.getStringLength("1qw申"), 5);
        assertEquals(StringUtil.getStringLength("1qw，申"), 7);
    }

    @Test
    public void getLengthSub() {
        String tmp = "shen，神水水水水是12s";
        assertEquals(StringUtil.getLengthSub(tmp, 4), "shen...");
        assertEquals(StringUtil.getLengthSub(tmp, -1), tmp);
        assertEquals(StringUtil.getLengthSub(tmp, -0), tmp);
        assertEquals(StringUtil.getLengthSub(tmp, 5), "shen...");
        assertEquals(StringUtil.getLengthSub(tmp, 6), "shen，...");
        assertEquals(StringUtil.getLengthSub(tmp, 100), tmp);
        assertEquals(StringUtil.getLengthSub(tmp, 21), tmp);
        assertEquals(StringUtil.getLengthSub(tmp, 20), "shen，神水水水水是12...");
    }
}