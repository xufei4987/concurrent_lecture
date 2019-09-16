package com.youxu.threadCommunication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mall {
    private int count = 0;

    public static final int CAPACITY = 10;

    public synchronized void produce() {
        while (count >= CAPACITY) {
            System.out.println(Thread.currentThread().getName() + "停止生产，库存已满");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count++;
        System.out.println(Thread.currentThread().getName() + "开始生产，当前库存为" + count);
        notifyAll();
    }

    public synchronized void consume() {
        while (count <= 0) {
            System.out.println(Thread.currentThread().getName() + "停止消费，库存已满");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        System.out.println(Thread.currentThread().getName() + "开始消费，当前库存为" + count);
        notifyAll();
    }

    public static void main(String[] args) {
        Mall mall = new Mall();
        ProduceTarget produceTarget = new ProduceTarget(mall);
        ConsumeTarget consumeTarget = new ConsumeTarget(mall);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            executorService.execute(produceTarget);
        }
        for (int i = 0; i < 5; i++) {
            executorService.execute(consumeTarget);
        }
    }
}

class ProduceTarget implements Runnable {

    private Mall mall;

    public ProduceTarget(Mall mall) {
        this.mall = mall;
    }

    @Override
    public void run() {
        while (true) {
            mall.produce();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ConsumeTarget implements Runnable {

    private Mall mall;

    public ConsumeTarget(Mall mall) {
        this.mall = mall;
    }

    @Override
    public void run() {
        while (true) {
            mall.consume();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
