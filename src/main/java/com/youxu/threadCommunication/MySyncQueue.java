package com.youxu.threadCommunication;

import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MySyncQueue<E> {
    private Lock lock = new ReentrantLock();
    Condition needRm = lock.newCondition();
    Condition needAdd = lock.newCondition();
    private int addIdx,removeIdx,count;
    private E[] arr;

    public MySyncQueue(int capacity){
        arr = (E[]) new Object[capacity];
    }

    public void add(E e){
        lock.lock();
        while (count == arr.length){
            try {
                needAdd.await();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        arr[addIdx] = e;
        addIdx++;
        count++;
        if(addIdx == arr.length){
            addIdx = 0;
        }
        needRm.signalAll();
        lock.unlock();
    }

    public E remove(){
        lock.lock();
        while (count == 0){
            try {
                needRm.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        E e = arr[removeIdx];
        removeIdx ++;
        count --;
        if(removeIdx == arr.length){
            removeIdx = 0;
        }
        needAdd.signalAll();
        lock.unlock();
        return e;
    }

    public static void main(String[] args) {
        MySyncQueue<String> stringMySyncQueue = new MySyncQueue<>(10);
        Runnable runnable = ()->{
            while (true){
                stringMySyncQueue.add(UUID.randomUUID().toString());
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
        new Thread(()-> {
            while (true){
                System.out.println(stringMySyncQueue.remove());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}
