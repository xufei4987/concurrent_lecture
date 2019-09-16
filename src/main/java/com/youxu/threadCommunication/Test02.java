package com.youxu.threadCommunication;

public class Test02 {

    private Object lock = new Object();

    public static void main(String[] args) {
        Test02 test01 = new Test02();
        new Thread(() -> {
            synchronized (test01.lock) {
                try {
                    test01.lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始执行");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "结束执行");
            }


        }).start();

        new Thread(() -> {
            synchronized (test01.lock) {
                System.out.println(Thread.currentThread().getName() + "开始执行");
                test01.lock.notify();
            }
        }).start();
    }
}
