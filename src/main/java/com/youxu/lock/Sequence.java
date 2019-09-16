package com.youxu.lock;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/29 10:39
 * 内置锁、互斥锁
 * synchronized关键字可以修饰在实例方法、静态方法以及代码块上
 * 修饰实例方法：对this加锁 ACC_SYNCHRONIZED
 * 修饰静态方法：对当前类的字节码对象加锁 ACC_SYNCHRONIZED
 * 修饰代码块：对传入的对象加锁 monitorenter monitorexit
 * 任何对象都可以作为锁，锁信息存储在对象头中
 * 对象头中的信息：Mark Word（根据标志位进行空间复用）、 Class Metadata Address、 Array Length
 * synchronized也能保证可见性
 * <p>
 * 偏向锁：每次获取锁和释放锁都会浪费资源； 大多情况下锁都是由同一个线程获得的；
 * 偏向锁会在mark word中记录第一次获取锁线程的id以及锁的标志位，并当前线程一直持有该锁不释放，以避免重复获取锁和释放锁带来的性能消耗，直到竞争的产生；
 * 偏向锁的应用场景：只有一个线程访问同步代码块
 * <p>
 * 轻量级锁：
 * <p>
 * 重量级锁：
 **/
public class Sequence {

    private int value;

    public synchronized int getNext() {
        return ++value;
    }

    public static void main(String[] args) {
        Sequence sequence = new Sequence();
        Runnable runnable = () -> {
            while (true) {
                System.out.println(sequence.getNext());
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
    }

}
