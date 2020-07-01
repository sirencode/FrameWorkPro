package com.syh.framework.algorithm;

/**
 * Created by shenyonghe on 2020/5/22.
 * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
 */
public class NumerOf1 {

    /**
     * 一个二进制数n减1后与原二进制数进行&运算( 即n&(n-1) )会消去最右边的1
     * n&(n-1)每次都消去最右边的1，最终1全被消去会得到0，所以有几个1就可以进行几次n&(n-1)
     * @param n
     * @return
     */
    public static int NumberOf1(int n) {
        int count = 0;
        while(n!=0){
            count++;
            n=n&(n-1);
        }
        return count;
    }
}
