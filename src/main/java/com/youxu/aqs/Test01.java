package com.youxu.aqs;

import java.util.concurrent.locks.Lock;

public class Test01 {

    private int value;

    private Lock lock = new MyLock();

    public int next() {
        try {
            lock.lock();
            return ++value;
        } finally {
            lock.unlock();
        }

    }

    public void a() {
        try {
            lock.lock();
            System.out.println("a");
            b();
        } finally {
            lock.unlock();
        }

    }

    private void b() {
        try {
            lock.lock();
            System.out.println("b");
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        Test01 test01 = new Test01();
//        Runnable runnable = () -> {
//            while (true){
//                System.out.println(test01.next());
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        new Thread(runnable).start();
//        new Thread(runnable).start();
//        new Thread(runnable).start();
        test01.a();
    }
}
