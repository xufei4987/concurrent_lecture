package com.youxu.threadCommunication;

public class Test01 {
    private volatile boolean flag = false;

    public static void main(String[] args) {
        Test01 test01 = new Test01();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始执行");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "结束执行");
            test01.flag = true;
        }).start();

        new Thread(() -> {
            while (!test01.flag) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "开始执行");
        }).start();
    }
}
