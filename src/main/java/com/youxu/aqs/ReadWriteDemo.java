package com.youxu.aqs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *读写互斥、写读互斥、写写互斥、读读共享
 */
public class ReadWriteDemo {
    private Map<String, Object> map = new HashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Lock readLock = readWriteLock.readLock();

    private Lock writeLock = readWriteLock.writeLock();

    private boolean flag = false;

    public Object get(String key){
        try {
            readLock.lock();
            System.out.println(Thread.currentThread() + " 正在读");
            Thread.sleep(3000);
            return map.get(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread() + " 完成读");
        }
    }

    public void put(String key, Object object){
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread() + " 正在写");
            Thread.sleep(3000);
            map.put(key,object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread() + " 完成写");
        }
    }

    //锁降级  写锁降级为读锁
    public void readwrite(){
        readLock.lock();
        if(flag){
            readLock.unlock();

            writeLock.lock();
            map.put("xxx","2223");
            readLock.lock();
            writeLock.unlock();

            map.get("xxx");

            readLock.unlock();
        }


    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteDemo readWriteDemo = new ReadWriteDemo();
        readWriteDemo.put("0","aaa");
        new Thread(()->{
            System.out.println(readWriteDemo.get("0"));
        }).start();
        Thread.sleep(100);
        new Thread(()->{
            readWriteDemo.put("1","ttt");
        }).start();
//        new Thread(()->{
//            readWriteDemo.put("2","yyy");
//        }).start();
//        new Thread(()->{
//            System.out.println(readWriteDemo.get("1"));
//        }).start();
//        new Thread(()->{
//            System.out.println(readWriteDemo.get("2"));
//        }).start();
    }
}
