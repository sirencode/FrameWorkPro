package com.syh.framework.util;

/**
 * Created by shenyonghe on 2020-03-20.
 */
public class TsUtil {
    public static void main(String[] args) {
      listName(
              "https://hls3-l3.xvideos-cdn.com/189f08226ff13597c668b84c376e0756c9c332f0-1585030073/videos/hls/e7/a6/5f/e7a65ffad1b88df44b02dd15c5e8605d/hls-1080p-ad76d"
              ,44);
    }

    private static void listName(String path,int num) {
        for (int i = 0; i <= num; i++) {
            System.out.println(path+i+".ts");
        }
    }
}
