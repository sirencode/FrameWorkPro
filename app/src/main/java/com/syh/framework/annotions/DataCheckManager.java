package com.syh.framework.annotions;

public class DataCheckManager {
    /**
     * 开关控制是否需要校验
     */
    private static boolean open = true;

    public static void setOpen(boolean isOpen) {
        open = isOpen;
    }

    public static boolean isOpen() {
        return open;
    }

    public static void checkValue(Object o,String path) {
        if (o != null && open && o instanceof NeedCheck) {
            ((NeedCheck)o).check(path);
        }
    }
}
