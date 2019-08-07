package com.youxu.lock;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/29 16:16
 * volatile关键字保证可见性的原理：
 * 将当前处理器缓存行的内容写回到系统内存。
 * 写回内存的操作会使在其他CPU里缓存了该内存地址的数据失效。
 *
 * happens-before规则：
 * 1、程序顺序规则
 * 2、监视器规则
 * 3、volatile变量规则
 * 4、传递性规则
 * 5、start规则
 * 6、join规则
 *
 * final域的内存语义：
 * 对于final域，编译器和处理器要遵循两个重拍序规则：
 * 1.在构造函数内对一个final域的写入，与随后把这个被构造对象的引用赋值给一个引用变量，这两个操作之间不能重排序。
 * 2.初次读一个包含final域的对象的应用，与随后初次读这个final域，这两个操作之间不能重排序
 *
 * 写final域的重排序规则禁止把final域的写重排序到构造函数之外。这个规则的实现包含两个方面：
 * 1.JMM禁止编译器把final域的写重排序到构造函数之外
 * 2.编译器会在final域的写入之后，构造函数return之前，插入一个StoreStore屏障。这个屏障禁止处理器把final域的写重排序到构造函数之外
 *
 * 读final域的重排序规则是：在一个线程中，初次读对象的引用与初次读这个对象包含的final域，
 * JMM禁止重排序这两个操作(该规则仅仅针对处理器)。编译器会在读final域的操作前面加一个LoadLoad屏障。
 **/
public class VolatileDemo {
    public volatile boolean run = false;

    public static void main(String[] args) {
        VolatileDemo volatileDemo = new VolatileDemo();

        new Thread(() -> {
            for(int i = 0; i < 10; i++){
                System.out.println(Thread.currentThread().getName() + "执行了" + i + "次");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            volatileDemo.run = true;
        }).start();

        new Thread(() -> {
            while (!volatileDemo.run){

            }
            System.out.println(Thread.currentThread().getName()+"执行了");
        }).start();
    }
}
