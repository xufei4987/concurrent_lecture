package com.youxu.juclock;

import java.util.concurrent.locks.Lock;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/30 19:19
 **/
public class ReentryLockDemo {
    private Lock lock = new MyLock();

    public void method1(){
        lock.lock();
        System.out.println("method1 invoked...");
        method2();
        lock.unlock();
    }

    public void method2(){
        lock.lock();
        System.out.println("method2 invoked...");
        method3();
        lock.unlock();
    }

    public void method3(){
        lock.lock();
        System.out.println("method3 invoked...");
        lock.unlock();
    }

    public static void main(String[] args) {
        ReentryLockDemo reentryLockDemo = new ReentryLockDemo();
        reentryLockDemo.method1();
    }
}
