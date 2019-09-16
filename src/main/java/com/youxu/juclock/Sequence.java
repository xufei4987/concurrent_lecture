package com.youxu.juclock;

import java.util.concurrent.locks.Lock;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/30 14:19
 * Lock的特点：
 * 1、需要显式的获取锁与释放锁 lock unlock
 * 2、非阻塞获取锁 trylock
 * 3、公平锁 ReentrantLock(boolean fair)
 * 4、能被中断的获取锁 lockInterruptibly
 * 5、超时获取锁 trylock（time）
 **/
public class Sequence {

    private int value;

    private Lock lock = new MyLock();


    public int getNext() {
        try {
            lock.lock();
            ++value;
        } finally {
            lock.unlock();
        }
        return value;

    }

    public static void main(String[] args) {
        Sequence sequence = new Sequence();
        Runnable runnable = () -> {
            while (true) {
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
