package com.youxu.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/8/5 14:53
 **/
public class ForkJoinDemo extends RecursiveTask<Integer> {

    private int begin;
    private int end;

    public ForkJoinDemo(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask forkJoinTask = forkJoinPool.submit(new ForkJoinDemo(1,5000000));
        long start1 = System.currentTimeMillis();
        System.out.println("fork-join计算结果:" + forkJoinTask.get());
        long end1 = System.currentTimeMillis();
        int sum = 0;
        long start2 = System.currentTimeMillis();
        for (int i = 1; i <= 5000000; i++){
            sum += i;
        }
        System.out.println("单线程计算结果:" + sum);
        long end2 = System.currentTimeMillis();
        System.out.println(end1-start1);
        System.out.println(end2-start2);
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if(begin +1 == end){
            sum = begin + end;
        }else if(begin == end){
            sum = begin;
        }else {

            ForkJoinDemo fork1 = new ForkJoinDemo(begin, (begin + end) / 2);
            ForkJoinDemo fork2 = new ForkJoinDemo((begin + end) / 2 + 1, end);

            fork1.fork();
            fork2.fork();

            Integer join1 = fork1.join();
            Integer join2 = fork2.join();

            sum = join1 + join2;

        }
        return sum;
    }
}
