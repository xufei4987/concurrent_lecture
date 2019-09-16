package com.youxu.lock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/29 12:30
 **/
public class SpinLockDemo {
    public static CountDownLatch countDownLatch = new CountDownLatch(5);

    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            System.out.println(Thread.currentThread().getName() + ":开始执行。。。");
            try {
                Thread.sleep(new Random().nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + ":执行结束。。。");
        };
        Thread t1 = new Thread(r);
        t1.start();
        Thread t2 = new Thread(r);
        t2.start();
        Thread t3 = new Thread(r);
        t3.start();
        Thread t4 = new Thread(r);
        t4.start();
        Thread t5 = new Thread(r);
        t5.start();

//        t1.join();
//        t2.join();
//        t3.join();
//        t4.join();
//        t5.join();
        countDownLatch.await();

        System.out.println("所有线程执行完毕。。。");
    }
}
