package com.youxu.juc;

/**
 * 1、并发与并行
 * 并发：Concurrency，是并发的意思。并发的实质是一个物理CPU(也可以多个物理CPU) 在若干道程序（或线程）之间多路复用，
 *      并发性是对有限物理资源强制行使多用户共享以提高效率。
 *      从微观角度来讲：所有的并发处理都有排队等候，唤醒，执行等这样的步骤，在微观上他们都是序列被处理的，
 *      如果是同一时刻到达的请求（或线程）也会根据优先级的不同，而先后进入队列排队等候执行。
 *      从宏观角度来讲：多个几乎同时到达的请求（或线程）在宏观上看就像是同时在被处理。
 * 并行：Parallelism，翻译过来即并行，指两个或两个以上事件（或线程）在同一时刻发生，是真正意义上的不同事件或线程在同一时刻，
 *      在不同CPU资源上（多核），同时执行。并行，不存在像并发那样竞争，等待的概念。
 *
 * 2、线程与进程
 * 进程是资源分配的基本单位，一个进程包含多个线程，线程共享进程的资源
 * 线程是处理器调度的基本单位
 * 3、多线程一定会快吗？
 * 不一定，线程间切换会有一定的时间成本
 * 4、线程的状态
 * 1）初始化状态 new
 * 2）就绪状态 runnable
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
