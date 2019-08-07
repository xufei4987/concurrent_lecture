package com.youxu.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/29 11:24
 * 会产生线程安全问题：
 * 在getInstance加上synchronized关键字，但在高并发情况下，效率较低。
 **/
public class SingletonDemo2 {
    private SingletonDemo2(){}

    private static SingletonDemo2 instance;

    public static synchronized SingletonDemo2 getInstance(){
        if(instance == null){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new SingletonDemo2();
        }
        return instance;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 20; i++){
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + " " + SingletonDemo2.getInstance()));
        }
        executorService.shutdown();
    }
}
