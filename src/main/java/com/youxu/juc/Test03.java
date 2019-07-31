package com.youxu.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Test03 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("computing .....");
        Thread.sleep(2000);
        return 1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> integerFutureTask = new FutureTask<>(new Test03());
        new Thread(integerFutureTask).start();
        System.out.println("do other jobs...");
        Integer integer = integerFutureTask.get();
        System.out.println("result is " + integer);
    }
}
