package com.youxu.juclock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/30 14:33
 * 我们在调用wait()方法的时候，心里想的肯定是因为当前方法不满足我们指定的条件，
 * 因此执行这个方法的线程需要等待直到其他线程改变了这个条件并且做出了通知。
 * 那么为什么要把wait()方法放在循环而不是if判断里呢，其实答案显而易见，
 * 因为wait()的线程永远不能确定其他线程会在什么状态下notify()，
 * 所以必须在被唤醒、抢占到锁并且从wait()方法退出的时候再次进行指定条件的判断，
 * 以决定是满足条件往下执行呢还是不满足条件再次wait()呢.
 * !!!!永远都要把wait()放到循环语句里面!!!!
 * !!!!之所以我们应该尽量使用notifyAll()的原因就是，notify()非常容易导致死锁!!!!
 * 当然notifyAll并不一定都是优点，毕竟一次性将Wait Set中的线程都唤醒是一笔不菲的开销，
 * 如果你能handle你的线程调度，那么使用notify()也是有好处的.
 * <p>
 * <p>
 * 自定义可重入锁
 **/
public class MyLock implements Lock {
    //锁标志位
    private boolean locked = false;
    //持有锁的线程
    private Thread lockby = null;
    //持有锁的次数
    private int lockCount = 0;

    @Override
    public synchronized void lock() {
        while (locked && lockby != Thread.currentThread()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        locked = true;
        lockCount++;
        lockby = Thread.currentThread();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public synchronized void unlock() {
        if (lockby == Thread.currentThread()) {
            lockCount--;
            if (lockCount == 0) {
                locked = false;
                lockby = null;
                notifyAll();
            }
        }

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
