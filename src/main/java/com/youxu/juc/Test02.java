package com.youxu.juc;

public class Test02 implements Runnable {
    @Override
    public void run() {
        System.out.println("thread1 running...");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Test02());
        thread.start();

        new Thread() {
            @Override
            public void run() {
                System.out.println("thread2 running...");
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread3 running...");
            }
        }).start();

        new Thread(() -> System.out.println("thread4 running...")).start();
    }
}
