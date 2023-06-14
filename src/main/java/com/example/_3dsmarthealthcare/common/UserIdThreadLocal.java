package com.example._3dsmarthealthcare.common;

/**
 * 利用 threadLocal 把拦截器中的对象传递到controller或service中
 * 相当于一个容器，此容器伴随着线程，线程启动，就有这个容器，销毁，容器就跟着销毁。生命周期就是这个线程。
 */
public class UserIdThreadLocal {
    private UserIdThreadLocal() {
    }

    private static final ThreadLocal<String> LOCAL = new ThreadLocal<String>();

    public static void set(String userId) {
        LOCAL.set(userId);
    }

    public static String get() {
        return LOCAL.get();
    }
}
