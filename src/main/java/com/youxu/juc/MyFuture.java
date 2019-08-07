package com.youxu.juc;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/8/5 11:09
 **/
public class MyFuture {
    private Product product;
    private boolean done;

    public synchronized Product getProduct() {
        while (!done) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return product;
    }

    public synchronized void setProduct(Product product) {
        this.product = product;
        done = true;
        notifyAll();
    }
}
