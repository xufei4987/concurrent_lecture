package com.youxu.threadpool;

import java.util.concurrent.*;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/8/6 11:05
 * ThreadPoolExecutor的核心参数为：
 * 1、int corePoolSize （core：核心的）= > 该线程池中核心线程数最大值
 * 什么是核心线程：线程池新建线程的时候，如果当前线程总数小于 corePoolSize ，
 * 则新建的是核心线程；如果超过corePoolSize，则新建的是非核心线程。
 *
 * 核心线程默认情况下会一直存活在线程池中，即使这个核心线程啥也不干(闲置状态)。
 *
 * 如果指定ThreadPoolExecutor的 allowCoreThreadTimeOut 这个属性为true，
 * 那么核心线程如果不干活(闲置状态)的话，超过一定时间( keepAliveTime)，就会被销毁掉
 *
 * 2、int maximumPoolSize  = >  该线程池中线程总数的最大值
 * 线程总数计算公式 = 核心线程数 + 非核心线程数。
 *
 * 3、long keepAliveTime  = >  该线程池中非核心线程闲置超时时长
 * 注意：一个非核心线程，如果不干活(闲置状态)的时长，超过这个参数所设定的时长，就会被销毁掉。
 * 但是，如果设置了 allowCoreThreadTimeOut = true，则会作用于核心线程。
 *
 * 4、TimeUnit unit  = > （时间单位）
 * 首先，TimeUnit是一个枚举类型，翻译过来就是时间单位，我们最常用的时间单位包括：
 * MILLISECONDS：毫秒， SECONDS：秒， MINUTES：分， HOURS：小时， DAYS：天
 *
 * 5、BlockingQueue<Runnable> workQueue = > 阻塞的队列
 * 该线程池中的任务队列：维护着等待执行的Runnable对象。当所有的核心线程都在干活时，
 * 新添加的任务会被添加到这个队列中等待处理，***如果队列满了，则新建非核心线程执行任务***
 *
 * BlockingQueue常用的四种实现的说明：
 *
 * SynchronousQueue：（同步队列）这个队列接收到任务的时候，会直接提交给线程处理，而不保留它（名字定义为 同步队列）。
 * 但有一种情况，假设所有线程都在工作怎么办？这种情况下，SynchronousQueue就会新建一个线程来处理这个任务。
 * 所以为了保证不出现（线程数达到了maximumPoolSize而不能新建线程）的错误，使用这个类型队列的时候，
 * maximumPoolSize一般指定成Integer.MAX_VALUE，去规避这个使用风险。
 *
 * LinkedBlockingQueue（链表阻塞队列）：这个队列接收到任务的时候，如果当前线程数小于核心线程数，则新建线程(核心线程)处理任务；
 * 如果当前线程数等于核心线程数，则进入队列等待。由于这个队列没有最大值限制，即所有超过核心线程数的任务都将被添加到队列中，
 * 这也就导致了maximumPoolSize的设定失效，因为总线程数永远不会超过corePoolSize。
 *
 * ArrayBlockingQueue（数组阻塞队列）：可以限定队列的长度（既然是数组，那么就限定了大小），接收到任务的时候，
 * 如果没有达到corePoolSize的值，则新建线程(核心线程)执行任务，如果达到了，则入队等候，如果队列已满，
 * 则新建线程(非核心线程)执行任务，又如果总线程数到了maximumPoolSize，并且队列也满了，则发生错误。
 *
 * DelayQueue（延迟队列）：队列内元素必须实现Delayed接口，这就意味着你传进去的任务必须先实现Delayed接口。
 * 这个队列接收到任务时，首先先入队，只有达到了指定的延时时间，才会执行任务
 *
 * 6、ThreadFactory threadFactory = > 创建线程的方式
 * 这是一个接口，new它的时候需要实现他的Thread newThread(Runnable r)方法
 *
 * 7、RejectedExecutionHandler handler = > 拒绝异常处理器
 * 这是一个接口，有四个默认的实现：
 * 1）CallerRunsPolicy：不抛出异常，通过调用handler的线程来执行这个任务
 * 2）AbortPolicy：抛出RejectedExecutionException异常，并丢弃该任务
 * 3）DiscardPolicy：直接丢弃该任务，不抛出任何异常
 * 4）DiscardOldestPolicy：丢弃队列中最老的任务，并调用threadPoolExecutor.execute(r)方法处理该任务
 *
 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * newCachedThreadPool 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * newScheduledThreadPool 创建一个定长任务线程池，支持定时及周期性任务执行。
 * newSingleThreadExecutor 创建一个单线程的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 **/
public class ThreadPoolDemo {

    public static void main(String[] args) {
        //10个线程来处理大量任务
        ThreadPoolExecutor threadPoolExecutor1 = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        //1个线程来处理大量任务
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //带缓存的线程池
        ThreadPoolExecutor threadPoolExecutor3 = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    }
}
