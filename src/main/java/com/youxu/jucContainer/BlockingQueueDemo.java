package com.youxu.jucContainer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/8/6 10:00
 **/
public class BlockingQueueDemo {
    private BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);


    public static void main(String[] args) {

        BlockingQueueDemo blockingQueueDemo = new BlockingQueueDemo();

        Runnable r1 = () -> {
            while (true) {
                try {
                    blockingQueueDemo.blockingQueue.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable r2 = () -> {
            while (true) {
                try {
                    blockingQueueDemo.blockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        new Thread(r1).start();
        new Thread(r1).start();
        new Thread(r1).start();
        new Thread(r2).start();
        new Thread(r2).start();

        while (true) {
            System.out.println(blockingQueueDemo.blockingQueue.size());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
