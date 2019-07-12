package com.syh.framework.design_patters.create_type;

/**
 * 确保某一个类只有一个实例，并且提供一个全局访问点（只能在一个进程内生效）
 */
public class SingleInstance {

    /**
     * 饿汉式单例类
     */
    public static class EagerSingleton {
        private static final EagerSingleton instance = new EagerSingleton();

        private EagerSingleton() {
        }

        public static EagerSingleton getInstance() {
            return instance;
        }
    }

    /**
     * 懒汉+
     */
    public static class LazySingleton {
        private volatile static LazySingleton instance = null;

        private LazySingleton() {
        }

        public static LazySingleton getInstance() {
            //第一重判断
            if (instance == null) {
                //锁定代码块
                synchronized (LazySingleton.class) {
                    //第二重判断
                    if (instance == null) {
                        instance = new LazySingleton(); //创建单例实例
                    }
                }
            }
            return instance;
        }
    }

    /**
     * 更好的静态内部类实现
     */
    public static class Singleton {
        private Singleton() {
        }

        private static class HolderClass {
            private final static Singleton instance = new Singleton();
        }

        public static Singleton getInstance() {
            return HolderClass.instance;
        }
    }
}

