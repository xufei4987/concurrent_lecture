package com.youxu.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test05 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++){
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        executorService.shutdown();
    }
}
