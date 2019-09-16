package com.youxu.lock;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/29 12:20
 **/
public class ReentryLockDemo {

    public synchronized void a() {
        System.out.println("method a");
        b();
    }

    public synchronized void b() {
        System.out.println("method b");
    }

    public static void main(String[] args) {
        new Thread(() -> {
            ReentryLockDemo reentryLockDemo = new ReentryLockDemo();
            reentryLockDemo.a();
        }).start();
    }
}
