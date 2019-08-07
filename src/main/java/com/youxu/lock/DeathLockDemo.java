package com.youxu.lock;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/29 14:40
 **/
public class DeathLockDemo {
    private Object object1 = new Object();
    private Object object2 = new Object();

    public void a(){
        synchronized (object1){
            System.out.println(Thread.currentThread().getName() + ":获取锁object1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object2){
                System.out.println(Thread.currentThread().getName() + ":获取锁object2");
            }
        }
    }

    public void b(){
        synchronized (object2){
            System.out.println(Thread.currentThread().getName() + ":获取锁object2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object1){
                System.out.println(Thread.currentThread().getName() + ":获取锁object1");
            }
        }
    }

    public static void main(String[] args) {
        DeathLockDemo deathLockDemo = new DeathLockDemo();
        new Thread(() -> deathLockDemo.a()).start();
        new Thread(() -> deathLockDemo.b()).start();
    }
}
