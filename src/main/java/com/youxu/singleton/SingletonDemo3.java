package com.youxu.singleton;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/29 11:38
 * 双重锁校验：虽然能够懒加载，也能够提高并发性能，但jvm的指令重拍任然有可能带来线程安全问题。
 * 解决方法：instance加上volatile关键字，禁止指令重拍。
 * 我们知道new Singleton()是一个非原子操作，编译器可能会重排序
 * 【构造函数可能在整个对象初始化完成前执行完毕，即赋值操作（只是在内存中开辟一片存储区域后直接返回内存的引用）在初始化对象前完成】。
 * 而线程B在线程A赋值完时判断instance就不为null了，此时B拿到的将是一个没有初始化完成的半成品。
 *
 **/
public class SingletonDemo3 {
    private SingletonDemo3(){

    }
    private static volatile SingletonDemo3 instance;

    public static SingletonDemo3 getInstance(){
        if(instance == null){
            synchronized (SingletonDemo3.class){
                if(instance == null){
                    instance = new SingletonDemo3();
                }
            }
        }
        return instance;
    }
}
