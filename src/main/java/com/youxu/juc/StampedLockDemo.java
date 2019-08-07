package com.youxu.juc;

import java.util.concurrent.locks.StampedLock;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/8/7 9:37
 * 1、引入StampdLock的原因
 * ReentrantReadWriteLock使得多个读线程同时持有读锁（只要写锁未被占用），而写锁是独占的。
 * 但是，读写锁如果使用不当，很容易产生“饥饿”问题：
 * 比如在读线程非常多，写线程很少的情况下，很容易导致写线程“饥饿”，虽然使用“公平”策略可以一定程度上缓解这个问题，
 * 但是“公平”策略是以牺牲系统吞吐量为代价的。
 *
 * 2、StampedLock的特点
 * 1)所有获取锁的方法，都返回一个邮戳（Stamp），Stamp为0表示获取失败，其余都表示成功；
 * 2)所有释放锁的方法，都需要一个邮戳（Stamp），这个Stamp必须是和成功获取锁时得到的Stamp一致；
 * 3)StampedLock是不可重入的；（如果一个线程已经持有了写锁，再去获取写锁的话就会造成死锁）
 * 4)StampedLock有三种访问模式：
 *      ①Reading（读模式）：功能和ReentrantReadWriteLock的读锁类似
 *      ②Writing（写模式）：功能和ReentrantReadWriteLock的写锁类似
 *      ③Optimistic reading（乐观读模式）：这是一种优化的读模式。
 * 5)StampedLock支持读锁和写锁的相互转换
 * 我们知道RRW中，当线程获取到写锁后，可以降级为读锁，但是读锁是不能直接升级为写锁的。
 * StampedLock提供了读锁和写锁相互转换的功能，使得该类支持更多的应用场景。
 * 6)无论写锁还是读锁，都不支持Conditon等待
 *
 * 3、“Optimistic reading”的使用必须遵循以下模式：
 * long stamp = lock.tryOptimisticRead();  // 非阻塞获取版本信息
 * copyVaraibale2ThreadMemory();           // 拷贝变量到线程本地堆栈
 * if(!lock.validate(stamp)){              // 校验
 *     long stamp = lock.readLock();       // 获取读锁
 *     try {
 *         copyVaraibale2ThreadMemory();   // 拷贝变量到线程本地堆栈
 *      } finally {
 *        lock.unlock(stamp);              // 释放悲观锁
 *     }
 * }
 * useThreadMemoryVarables();              // 使用线程本地堆栈里面的数据进行操作
 **/
public class StampedLockDemo {
    public static void main(String[] args) {

    }
    static class Point {
        private double x, y;
        private final StampedLock sl = new StampedLock();

        void move(double deltaX, double deltaY) {
            long stamp = sl.writeLock();    //涉及对共享资源的修改，使用写锁-独占操作
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                sl.unlockWrite(stamp);
            }
        }

        /**
         * 使用乐观读锁访问共享资源
         * 注意：乐观读锁在保证数据一致性上需要拷贝一份要操作的变量到方法栈，并且在操作数据时候可能其他写线程已经修改了数据，
         * 而我们操作的是方法栈里面的数据，也就是一个快照，所以最多返回的不是最新的数据，但是一致性还是得到保障的。
         *
         * @return
         */
        double distanceFromOrigin() {
            long stamp = sl.tryOptimisticRead();    // 使用乐观读锁
            double currentX = x, currentY = y;      // 拷贝共享资源到本地方法栈中
            if (!sl.validate(stamp)) {              // 如果有写锁被占用，可能造成数据不一致，所以要切换到普通读锁模式
                stamp = sl.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    sl.unlockRead(stamp);
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }

        void moveIfAtOrigin(double newX, double newY) { // upgrade
            // Could instead start with optimistic, not read mode
            long stamp = sl.readLock();
            try {
                while (x == 0.0 && y == 0.0) {
                    long ws = sl.tryConvertToWriteLock(stamp);  //读锁转换为写锁
                    if (ws != 0L) {
                        stamp = ws;
                        x = newX;
                        y = newY;
                        break;
                    } else {
                        sl.unlockRead(stamp);
                        stamp = sl.writeLock();
                    }
                }
            } finally {
                sl.unlock(stamp);
            }
        }
    }
}
