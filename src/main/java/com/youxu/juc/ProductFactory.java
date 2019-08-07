package com.youxu.juc;

import java.util.Random;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/8/5 11:09
 **/
public class ProductFactory {

    public static MyFuture createProduct(String name){
        MyFuture myFuture = new MyFuture();
        new Thread(()->{
            Product product = new Product(new Random().nextInt(), name);
            myFuture.setProduct(product);
        }).start();
        return myFuture;
    }

}
