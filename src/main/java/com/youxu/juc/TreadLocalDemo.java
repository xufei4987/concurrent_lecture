package com.youxu.juc;

import java.util.Random;

public class TreadLocalDemo {

    private ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> new Integer(0));

    public int getNext() {
        Integer val = tl.get();
        val++;
        tl.set(val);
        return val;
    }

    public static void main(String[] args) {
        TreadLocalDemo treadLocalDemo = new TreadLocalDemo();
        Runnable r = () -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":" + treadLocalDemo.getNext());
                int i = new Random().nextInt(2000);
                try {
                    Thread.sleep(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();
    }
}
