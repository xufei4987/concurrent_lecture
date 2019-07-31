package com.youxu.threadCommunication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ABCDemo1 {

    private Lock lock = new ReentrantLock();
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();
    private int signal =1;

    public void a(){
        lock.lock();
        while (signal != 1){
            try {
                conditionA.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("a");
        signal = 2;
        conditionB.signal();
        lock.unlock();
    }

    public void b(){
        lock.lock();
        while (signal != 2){
            try {
                conditionB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("b");
        signal = 3;
        conditionC.signal();
        lock.unlock();
    }
    public void c(){
        lock.lock();
        while (signal != 3){
            try {
                conditionC.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("c");
        signal = 1;
        conditionA.signal();
        lock.unlock();
    }

    public static void main(String[] args) {
        ABCDemo1 abcDemo = new ABCDemo1();
        TaskA1 taskA = new TaskA1(abcDemo);
        TaskB1 taskB = new TaskB1(abcDemo);
        TaskC1 taskC = new TaskC1(abcDemo);
        new Thread(taskA).start();
        new Thread(taskB).start();
        new Thread(taskC).start();
    }

}

class TaskA1 implements Runnable{
    private ABCDemo1 abcDemo;

    public TaskA1(ABCDemo1 abcDemo) {
        this.abcDemo = abcDemo;
    }

    @Override
    public void run() {
        while (true){
            abcDemo.a();
        }
    }
}

class TaskB1 implements Runnable{
    private ABCDemo1 abcDemo;

    public TaskB1(ABCDemo1 abcDemo) {
        this.abcDemo = abcDemo;
    }

    @Override
    public void run() {
        while (true){
            abcDemo.b();
        }
    }
}

class TaskC1 implements Runnable{
    private ABCDemo1 abcDemo;

    public TaskC1(ABCDemo1 abcDemo) {
        this.abcDemo = abcDemo;
    }

    @Override
    public void run() {
        while (true){
            abcDemo.c();
        }
    }
}