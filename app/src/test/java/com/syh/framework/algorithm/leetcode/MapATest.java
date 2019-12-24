package com.syh.framework.algorithm.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by shenyonghe on 2019-12-07.
 */
public class MapATest {
    @Test
    public void intersection() {
        int[] nums1 = new int[]{1, 2, 2, 1};
        int[] nums2 = new int[]{2, 2};
        int[] result = MapA.intersection(nums1, nums2);
        assertEquals(result.length, 1);
        assertEquals(result[0], 2);
        System.out.println(Arrays.toString(result));
    }

    @Test
    public void intersection2() {
        int[] nums1 = new int[]{4, 9, 5};
        int[] nums2 = new int[]{9, 4, 9, 8, 4};
        int[] result = MapA.intersection(nums1, nums2);
        assertEquals(result.length, 2);
        assertEquals(result[0], 4);
        assertEquals(result[1], 9);
        System.out.println(Arrays.toString(result));
    }

    @Test
    public void isHappy() {
        assertEquals(MapA.isHappy(19), true);
        assertEquals(MapA.isHappy(7), true);
        assertEquals(MapA.isHappy(9), false);
        assertEquals(MapA.isHappy(16), false);
    }

    @Test
    public void isIsomorphic() {
        assertEquals(MapA.isIsomorphic("egg", "add"), true);
        assertEquals(MapA.isIsomorphic("foo", "bar"), false);
        assertEquals(MapA.isIsomorphic("paper", "title"), true);
        assertEquals(MapA.isIsomorphic("ab", "aa"), false);
    }

    @Test
    public void findRestaurant() {
        String[] list1 = new String[]{"Shogun", "Tapioca Express", "Burger King", "KFC"};
        String[] list2 = new String[]{"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"};
        String[] list3 = new String[]{"KFC", "Shogun", "Burger King"};
        String[] list4 = new String[]{"KFC", "Burger King", "Tapioca Express", "Shogun"};
        assertEquals(MapA.findRestaurant(list1, list2).length, 1);
        assertEquals(MapA.findRestaurant(list1, list2)[0], "Shogun");

        assertEquals(MapA.findRestaurant(list1, list3).length, 1);
        assertEquals(MapA.findRestaurant(list1, list3)[0], "Shogun");

        assertEquals(MapA.findRestaurant(list1, list4).length, 4);
        assertEquals(MapA.findRestaurant(list1, list4)[0], "KFC");
        assertEquals(MapA.findRestaurant(list1, list4)[1], "Burger King");
        assertEquals(MapA.findRestaurant(list1, list4)[2], "Tapioca Express");
        assertEquals(MapA.findRestaurant(list1, list4)[3], "Shogun");
    }

    @Test
    public void containsNearbyDuplicate() {
        int[] nums = new int[]{1, 2, 3, 1};
        assertEquals(MapA.containsNearbyDuplicate(nums, 3), true);
        assertEquals(MapA.containsNearbyDuplicate(nums, 1), false);
    }

    @Test
    public void groupAnagrams() {
        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> lists = MapA.groupAnagrams(strs);
        assertEquals(lists.size(), 3);
    }
}