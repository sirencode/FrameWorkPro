package com.syh.framework.algorithm.sort;

import java.util.Arrays;

/**
 * Created by shenyonghe on 2019-10-31.
 * 时间复杂度：O(nlog2n)
 * 空间复杂度：O(1)
 * 稳定性：不稳定
 * 堆是具有以下性质的完全二叉树：
 * 大顶堆：每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆。
 * 小顶堆：每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。
 * 大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]
 * 小顶堆：arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]
 * 从最后一个非叶子结点开始（叶结点自然不用调整，第一个非叶子结点 arr.length/2-1），从左至右，从下至上进行调整(第一个非叶子，直到1)。
 * 建立大顶堆后，将0和最后一个节点替换位置继续。
 * 堆排序的基本思想是：
 * 1 将待排序序列构造成一个大顶堆，此时，整个序列的最大值就是堆顶的根节点。
 * 2 将其与末尾元素进行交换，此时末尾就为最大值。
 * 3 然后将剩余n-1个元素重新构造成一个堆，这样会得到n个元素的次小值。
 * 4 如此反复执行，便能得到一个有序序列了。
 */
public class HeapSort {
    private int[] arr;

    public HeapSort(int[] arr) {
        this.arr = arr;
    }

    /**
     * 堆排序的主要入口方法，共两步。
     */
    public void sort() {
        /*
         *  第一步：将数组堆化
         *  beginIndex = 第一个非叶子节点。
         *  从第一个非叶子节点开始即可。无需从最后一个叶子节点开始。
         *  叶子节点可以看作已符合堆要求的节点，根节点就是它自己且自己以下值为最大。
         */
        int len = arr.length - 1;
        int beginIndex = (arr.length >> 1) - 1;
        for (int i = beginIndex; i >= 0; i--)
            maxHeapify(i, len);
        /*
         * 第二步：对堆化数据排序
         * 每次都是移出最顶层的根节点A[0]，与最尾部节点位置调换，同时遍历长度 - 1。
         * 然后从新整理被换到根节点的末尾元素，使其符合堆的特性。
         * 直至未排序的堆长度为 0。
         */
        for (int i = len; i > 0; i--) {
            swap(0, i);
            maxHeapify(0, i - 1);
        }
    }

    private void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 调整索引为 index 处的数据，使其符合堆的特性。
     *
     * @param index 需要堆化处理的数据的索引
     * @param len   未排序的堆（数组）的长度
     */
    private void maxHeapify(int index, int len) {
        int li = (index << 1) + 1; // 左子节点索引
        int ri = li + 1;           // 右子节点索引
        int cMax = li;             // 子节点值最大索引，默认左子节点。
        if (li > len) return;      // 左子节点索引超出计算范围，直接返回。
        if (ri <= len && arr[ri] > arr[li]) // 先判断左右子节点，哪个较大。
            cMax = ri;
        if (arr[cMax] > arr[index]) {
            swap(cMax, index);      // 如果父节点被子节点调换，
            maxHeapify(cMax, len);  // 则需要继续判断换下后的父节点是否符合堆的特性。
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 1, 3, 9, 6, 8, 30, 100, 11};
        /**
         *                 7
         *            1        3
         *         9     6   8  30
         *      100 11
         *
         *  大顶堆：从最后一个非叶子结点开始（叶结点自然不用调整，第一个非叶子结点 arr.length/2-1，从左至右，从下至上进行调整。
         *  第一次
         *  index = 9/2 -1 = 3
         *  max【100,11】 = 100 100 > 9 位置替换
         *
         *                    7
         *               1        3
         *          100     6   8  30
         *        9    11
         *  index - 1 = 2
         *  max[100,6]和1比较替换，100和1替换后，导致[1,9,11]错乱，继续调整。max[9,11] 替换1 max[8,30]和3比较替换
         *
         *                    7
         *              100        30
         *           11     6     8   3
         *        9    1
         *
         *  index = 1
         *
         *                       100
         *                11             30
         *             9      6        8     3
         *          7     1
         *
         *  100 拿走 放到最后  将最后一个节点放到根节点继续
         *
         *                       1
         *              11              30
         *          9       6        8      3
         *       7
         */
        new HeapSort(arr).sort();
        System.out.println(Arrays.toString(arr));
    }
}
