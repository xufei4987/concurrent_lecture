package com.youxu.singleton;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/29 11:18
 * 饿汉式:在类的初始化阶段就会创建实例，没有懒加载的效果
 **/
public class SingletonDemo1 {

    private SingletonDemo1() {
    }

    private static SingletonDemo1 instance = new SingletonDemo1();

    public static SingletonDemo1 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        SingletonDemo1 instance1 = SingletonDemo1.getInstance();
        SingletonDemo1 instance2 = SingletonDemo1.getInstance();

        System.out.println(instance1 == instance2);
    }
}
