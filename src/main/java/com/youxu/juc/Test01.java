package com.youxu.juc;

/**
 * 1、多线程与并发
 * 多线程是并发编程的一种解决方案
 * 2、线程与进程
 * 进程是资源分配的基本单位，一个进程包含多个线程，线程共享进程的资源
 * 线程是处理器调度的基本单位
 * 3、多线程一定会快吗？
 * 不一定，线程间切换会有一定的时间成本
 * 4、线程的状态
 * 1）初始化状态 new
 * 2）就绪状态 runable
 * 3）运行状态 running
 * 4）等待状态 Waiting 限时等待状态 timed waited
 * 5）阻塞状态 blocked
 * 6）终止状态 terminal
 * 5、线程安全问题
 * 1）多线程环境下
 * 2）操作共享变量
 * 3）对资源进行非原子操作
 */
public class Test01 extends Thread{
    public Test01(String name){
        super(name);
    }
    @Override
    public void run() {
        while (!isInterrupted()){
            System.out.println(getName() + " running");
        }

    }

    public static void main(String[] args) {
        Test01 t1 = new Test01("t1");
        Test01 t2 = new Test01("t2");

        t1.start();
        t2.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();

    }

}
