package com.youxu.threadCommunication;

import java.util.concurrent.TimeUnit;

public class Test03 {
    public synchronized void get() {
        System.out.println(Thread.currentThread().getName() + "执行了get");

        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "执行get结束");
    }

    public synchronized void set() {
        System.out.println(Thread.currentThread().getName() + "执行了set");
        notifyAll();
        System.out.println(Thread.currentThread().getName() + "休眠3S");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Test03 test03 = new Test03();
        Target1 target1 = new Target1(test03);
        Target2 target2 = new Target2(test03);
        new Thread(target1).start();
        new Thread(target1).start();
        new Thread(target1).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(target2).start();


    }

}

class Target1 implements Runnable {

    private Test03 test03;

    public Target1(Test03 test03) {
        this.test03 = test03;
    }

    @Override
    public void run() {
        test03.get();
    }
}

class Target2 implements Runnable {

    private Test03 test03;

    public Target2(Test03 test03) {
        this.test03 = test03;
    }

    @Override
    public void run() {
        test03.set();
    }
}
