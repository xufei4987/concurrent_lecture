package com.youxu.juc;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/8/7 10:28
 * 1、为什么要引入LongAdder
 * 我们知道，AtomicLong是利用了底层的CAS操作来提供并发性的，比如addAndGet方法，它的逻辑是采用自旋的方式不断更新目标值，直到更新成功。
 * 在并发量较低的环境下，线程冲突的概率比较小，自旋的次数不会很多。但是，高并发环境下，
 * N个线程同时进行自旋操作，会出现大量失败并不断自旋的情况，此时AtomicLong的自旋会成为瓶颈。
 * 这就是LongAdder引入的初衷——解决高并发环境下AtomicLong的自旋瓶颈问题。
 * LongAdder的基本思路就是分散热点，将value值分散到一个数组中，不同线程会命中到数组的不同槽中，各个线程只对自己槽中的那个值进行CAS操作，
 * 这样热点就被分散了，冲突的概率就小很多。如果要获取真正的long值，只要将各个槽中的变量值累加返回。
 * <p>
 * 2、原理
 * AtomicLong是多个线程针对单个热点值value进行原子操作。而LongAdder是每个线程拥有自己的槽，各个线程一般只对自己槽中的那个值进行CAS操作。
 * 比如有三个ThreadA、ThreadB、ThreadC，每个线程对value增加10。
 * 对于AtomicLong，最终结果的计算始终是下面这个形式：
 * value=10+10+10=30
 * 但是对于LongAdder来说，内部有一个base变量，一个Cell[]数组。
 * base变量：非竞态条件下，直接累加到该变量上
 * Cell[]数组：竞态条件下，累加个各个线程自己的槽Cell[i]中
 * 最终结果的计算是下面这个形式：
 * value=base+(Cell[0] + ... + Cell[n])
 * <p>
 * 返回累加的和，也就是“当前时刻”的计数值
 * 此返回值可能不是绝对准确的，因为调用这个方法时还有其他线程可能正在进行计数累加，
 * 方法的返回时刻和调用时刻不是同一个点，在有并发的情况下，这个值只是近似准确的计数值
 * <p>
 * 高并发时，除非全局加锁，否则得不到程序运行中某个时刻绝对准确的值。
 **/
public class LongAdderDemo {
}
