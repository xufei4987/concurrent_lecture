package com.youxu.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/8/5 10:23
 * callable和runnable的区别：
 * runnable的run方法时异步执行的，是由线程执行的
 * callable的call方法是同步执行的，是由future的run方法执行的
 **/
public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> callable = () -> {
            Thread.sleep(10000);
            return 1;
        };
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
//    public static void main(String[] args) {
//        System.out.println("买蛋糕");
//        MyFuture myFuture = ProductFactory.createProduct("蛋糕");
//        System.out.println("拿到订单，并去做其他事");
//        System.out.println("通过订单获取蛋糕");
//        Product product = myFuture.getProduct();
//        System.out.println("获取" + product);
//    }
}
