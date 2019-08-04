package com.youxu.juc;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                String exchange = exchanger.exchange(Thread.currentThread().getName() + "-123");
                System.out.println(Thread.currentThread().getName() + "获取到的数据：" + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                String exchange = exchanger.exchange(Thread.currentThread().getName() + "-123");
                System.out.println(Thread.currentThread().getName() + "获取到的数据：" + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
