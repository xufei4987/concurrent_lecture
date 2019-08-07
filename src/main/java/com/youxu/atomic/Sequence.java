package com.youxu.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/29 17:42
 **/
public class Sequence {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private int[] ints = {2,1,3,4};

    private AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(ints);

    public int getNext(){
//        return atomicInteger.incrementAndGet();
        return atomicIntegerArray.addAndGet(3,10);
    }


    public static void main(String[] args) {
        Sequence sequence = new Sequence();
        Runnable runnable = () -> {
            while (true){
                System.out.println(sequence.getNext());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
