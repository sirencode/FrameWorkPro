package com.syh.framework.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shenyonghe on 2019-10-28.
 * 时间轮算法：固定时间计时，多维度。
 */
public class TimeWheel {
    /**
     * 一个时间槽的时间 单位：秒
     */
    private int tickMs;
    /**
     * 时间轮大小 刻度：0-(size-1)
     */
    private int wheelSize;
    /**
     * 时间轮当前指针
     */
    private int currentTimestamp;
    private Timer timer;
    private HashMap<Integer, List<String>> hashMap;

    private static final String TAG = "TAG-TimeWheel";

    public TimeWheel(int tickMs, int wheelSize) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        initHashMap();
    }

    public void start() {
        if (timer == null) {
            timer = new Timer();
        }
        currentTimestamp = 0;
        MyTimerTask task = new MyTimerTask("");
        // 即第一次执行是在当前时间的0秒钟之后，之后每隔一秒钟执行一次
        timer.schedule(task, 0, tickMs * 1000);
    }

    private void initHashMap() {
        if (hashMap != null) hashMap.clear();
        hashMap = new HashMap<>(wheelSize);
        for (int i = 0; i < wheelSize; i++) {
            hashMap.put(i, new ArrayList<>());
        }
    }

    public void stop() {
        timer.cancel();
    }

    public void clear() {
        initHashMap();
    }

    public void remove(String element) {
        for (Integer integer : hashMap.keySet()) {
            if (hashMap.get(integer) != null && hashMap.get(integer).size() > 0) {
                for (int i = 0; i < hashMap.get(integer).size(); i++) {
                    if (hashMap.get(integer).get(i).equals(element)) {
                        hashMap.get(integer).remove(i);
                        break;
                    }
                }
            }
        }
    }

    public class MyTimerTask extends TimerTask {

        private String name;

        public MyTimerTask(String inputName) {
            name = inputName;
        }

        @Override
        public void run() {
            currentTimestamp = (currentTimestamp + 1) % wheelSize;
            System.out.println(TAG + "Current exec currentTimestamp is " + currentTimestamp);
            List<String> array = hashMap.get(currentTimestamp);
            if (array != null && array.size() > 0) {
                for (String s : array) {
                    System.out.println(TAG + "do something-" + s);
                }
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public void add(int index, String element) {
        remove(element);
        hashMap.get(index).add(element);
    }

    public void add(String element) {
        remove(element);
        hashMap.get(currentTimestamp).add(element);
    }
}
