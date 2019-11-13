package com.syh.framework.algorithm.sort;

import java.util.Arrays;

/**
 * Created by shenyonghe on 2019-10-22.
 */
public class SortUtil {
    private static final int[] SOURCE = new int[]{7, 1, 3, 9, 6, 8, 30, 100, 11};

    /**
     * 冒泡排序
     * 平均时间复杂度：O(n^2)
     * 最坏时间复杂度：O(n^2)
     * 最好时间复杂度：O(n)
     * 空间复杂度：O(1) 新增
     * 稳定性：稳定
     * 描述：
     * 1 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
     * 2 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
     * 比较次数：(n-1)+(n-2)+....1 = n*(n-1)/2
     *
     * @param source
     * @return
     */
    public static int[] bubbleSort(int[] source) {
        System.out.println("未排序前：");
        System.out.println(Arrays.toString(source));
        int length = source.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (source[j] > source[j + 1]) {
                    int tmp = source[j + 1];
                    source[j + 1] = source[j];
                    source[j] = tmp;
                }
            }
        }
        System.out.println("排序后：");
        System.out.println(Arrays.toString(source));
        return source;
    }

    /**
     * 插入排序
     * 平均时间复杂度：O(n^2)
     * 最坏时间复杂度：O(n^2)
     * 最好时间复杂度：O(n)
     * 空间复杂度：O(1)
     * 稳定性：稳定
     * 描述：
     * 1 从第一个元素开始，该元素可以认为已经被排序；
     * 2 取出下一个元素，在已经排序的元素序列中从后向前扫描；
     * 3 如果该元素（已排序）大于新元素，将该元素移到下一位置；
     * 比较次数：(n-1)+(n-2)+....1 = n*(n-1)/2
     *
     * @param source
     * @return
     */
    public static int[] insertSort(int[] source) {
        int preIndex, current;
        for (int i = 1; i < source.length; i++) {
            preIndex = i - 1;
            current = source[i];
            while (preIndex >= 0 && source[preIndex] > current) {
                source[preIndex + 1] = source[preIndex];
                preIndex--;
            }
            source[preIndex + 1] = current;
        }
        return source;
    }

    /**
     * 归并排序 2-路归并。
     * <p>
     * 平均时间复杂度：O(nlog2n)
     * 最坏时间复杂度：O(nlog2n)
     * 最好时间复杂度：O(nlog2n)
     * 空间复杂度：O(n)
     * 稳定性：稳定
     * 描述
     * 1 申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列；
     * 2 设定两个指针，最初位置分别为两个已经排序序列的起始位置；
     * 3 比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置；
     * 4 重复步骤 3 直到某一指针到达序列尾；
     * 5 将另一序列剩下的所有元素直接复制到合并序列尾。
     * 第一次归并：{1,7},{3,9},{6,8},{30,100},{11} 比较次数4
     * 第二次归并：{1,3,7,9},{6,8,30,100},{11},比较次数7
     * 第三次归并：{1,3,6,7,8,9,30,100},{11} ,最多8次
     *
     * @param arrays
     * @param L      指向数组第一个元素
     * @param R      指向数组最后一个元素
     */
    public static int[] mergeSort(int[] arrays, int L, int R) {
        //如果只有一个元素，那就不用排序了
        if (L == R) {
            return arrays;
        } else {
            //取中间的数，进行拆分
            int M = (L + R) / 2;
            //左边的数不断进行拆分
            mergeSort(arrays, L, M);
            //右边的数不断进行拆分
            mergeSort(arrays, M + 1, R);
            //合并
            merge(arrays, L, M + 1, R);
        }
        return arrays;
    }

    /**
     * 合并数组
     *
     * @param arrays
     * @param L      指向数组第一个元素
     * @param M      指向数组分隔的元素
     * @param R      指向数组最后的元素
     */
    public static void merge(int[] arrays, int L, int M, int R) {
        //左边的数组的大小
        int[] leftArray = new int[M - L];
        //右边的数组大小
        int[] rightArray = new int[R - M + 1];
        //往这两个数组填充数据
        for (int i = L; i < M; i++) {
            leftArray[i - L] = arrays[i];
        }
        for (int i = M; i <= R; i++) {
            rightArray[i - M] = arrays[i];
        }
        int i = 0, j = 0;
        // arrays数组的第一个元素
        int k = L;
        //比较这两个数组的值，哪个小，就往数组上放
        while (i < leftArray.length && j < rightArray.length) {
            //谁比较小，谁将元素放入大数组中,移动指针，继续比较下一个
            // 等于的情况是保证“稳定”
            if (leftArray[i] <= rightArray[j]) {
                arrays[k] = leftArray[i];
                i++;
                k++;
            } else {
                arrays[k] = rightArray[j];
                j++;
                k++;
            }
        }
        //如果左边的数组还没比较完，右边的数都已经完了，那么将左边的数抄到大数组中(剩下的都是大数字)
        while (i < leftArray.length) {
            arrays[k] = leftArray[i];
            i++;
            k++;
        }
        //如果右边的数组还没比较完，左边的数都已经完了，那么将右边的数抄到大数组中(剩下的都是大数字)
        while (j < rightArray.length) {
            arrays[k] = rightArray[j];
            k++;
            j++;
        }
    }


    // 不稳定算法

    /**
     * 希尔排序也称递减增量排序算法
     * 希尔排序的时间复杂度受增量序列影响，不同的增量序列会有不同时间复杂度：N 为数组长度，K 为当前增量
     * 最好时间复杂度：O(n)
     * 1 {1,2,4,8,...}这种序列并不是很好的增量序列，使用这个增量序列的时间复杂度（最坏情形）是O(n^2)
     * 2 Hibbard提出了另一个增量序列{1,3,7，...,2^k-1}，这种序列的时间复杂度(最坏情形)为O(n^1.5)
     * 3 Sedgewick提出了几种增量序列，其中最好的一个序列是{1,5,19,41,109,...} O(nlog2n)和O(n^2)之间，大致为O(n^1.3)
     * 空间复杂度：O(1)
     * 稳定性：不稳定
     * 描述：n/2分组，进行插排，每次除2，直到1。
     * 第一次 k = n/2 = 4:
     * [7,6] [1,8] [3,30] [9,100] [11] 每组数据经过插入排序：[6,7] [1,8] [3,30] [9,100] [11]=> [6, 1, 3, 9, 7, 8, 30, 100, 11]
     * 第二次 k = 2
     * [6, 3, 7, 30, 11 ] [1, 9, 8, 100] 每组数据经过插入排序：[3, 6, 7, 11, 30 ] [1,8,9,100] => [3,1,6,8,7,9,11,100,30]
     * 第三次 k=1
     * [1, 3, 6, 7, 8, 9, 11, 30, 100]
     * @param arr
     * @return
     */
    public static int[] shellSort(int[] arr) {
        int length = arr.length;
        int temp;
        //增量每次减半
        for (int step = length / 2; step >= 1; step /= 2) {
            for (int i = step; i < length; i++) {
                temp = arr[i];
                int j = i - step;
                while (j >= 0 && arr[j] > temp) {
                    arr[j + step] = arr[j];
                    j -= step;
                }
                arr[j + step] = temp;
            }
        }
        return arr;
    }

    /**
     * 选择排序
     * 时间复杂度：(n-1)+(n-2)+....1 = n*(n-1)/2
     * 最好，最坏，平均时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     * 描述：
     * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
     * @param arr
     * @return
     */
    public static void selectionSort(int[] arr) {
        int min, temp, len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            min = i;//未排序序列中最小数据数组下标
            for (int j = i + 1; j < len; j++) { //在未排序元素中继续寻找最小元素，并保存其下标
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            if (min != i) {
                temp = arr[min]; //将最小元素放到已排序序列的末尾
                arr[min] = arr[i];
                arr[i] = temp;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("未排序前：");
        System.out.println(Arrays.toString(SOURCE));
        shellSort(SOURCE);
        System.out.println("排序后：");
        System.out.println(Arrays.toString(SOURCE));
    }
}
