package com.youxu.juc;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo{
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < 20; i++){
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "处理资源。。。");
                    Thread.sleep(2000);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
