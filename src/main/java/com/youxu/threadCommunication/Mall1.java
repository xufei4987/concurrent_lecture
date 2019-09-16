package com.youxu.threadCommunication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mall1 {
    private int count = 0;

    public static final int CAPACITY = 10;

    private Lock lock = new ReentrantLock();

    private Condition consumeCondition = lock.newCondition();
    private Condition produceCondition = lock.newCondition();

    public void produce() {
        lock.lock();
        while (count >= CAPACITY) {
            System.out.println(Thread.currentThread().getName() + "停止生产，库存已满");
            try {
                produceCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count++;
        System.out.println(Thread.currentThread().getName() + "开始生产，当前库存为" + count);
        consumeCondition.signalAll();
        lock.unlock();
    }

    public void consume() {
        lock.lock();
        while (count <= 0) {
            System.out.println(Thread.currentThread().getName() + "停止消费，库存已满");
            try {
                consumeCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        System.out.println(Thread.currentThread().getName() + "开始消费，当前库存为" + count);
        produceCondition.signalAll();
        lock.unlock();
    }

    public static void main(String[] args) {
        Mall1 mall = new Mall1();
        ProduceTarget1 produceTarget = new ProduceTarget1(mall);
        ConsumeTarget1 consumeTarget = new ConsumeTarget1(mall);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 6; i++) {
            executorService.execute(produceTarget);
        }
        for (int i = 0; i < 4; i++) {
            executorService.execute(consumeTarget);
        }
    }
}

class ProduceTarget1 implements Runnable {

    private Mall1 mall;

    public ProduceTarget1(Mall1 mall) {
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

class ConsumeTarget1 implements Runnable {

    private Mall1 mall;

    public ConsumeTarget1(Mall1 mall) {
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
