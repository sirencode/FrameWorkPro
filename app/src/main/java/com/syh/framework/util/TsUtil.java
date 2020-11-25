package com.syh.framework.util;

/**
 * Created by shenyonghe on 2020-03-20.
 */
public class TsUtil {
    public static void main(String[] args) {
      listName(
              "https://hls2-l3.xvideos-cdn.com/db7692aaa9283eba36c968e900fe45460abfc7be-1601122221/videos/hls/9f/8b/ac/9f8bac35d902b597367bd9ee3321970f/hls-1080p-616ff"
              ,67);
    }

    private static void listName(String path,int num) {
        for (int i = 0; i <= num; i++) {
            System.out.println(path+i+".ts");
        }
    }
}
