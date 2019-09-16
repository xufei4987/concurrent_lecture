package com.youxu.atomic;


import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/7/29 18:49
 **/
public class AtomicDemo {
    private AtomicReference<User> userAtomicReference = new AtomicReference<>();

    private AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    public static void main(String[] args) {
        AtomicDemo atomicDemo = new AtomicDemo();
        User youxu = new User("youxu", 28);
        atomicDemo.userAtomicReference.set(youxu);
        User user1 = atomicDemo.userAtomicReference.updateAndGet(user -> {
            user.setAge(29);
            return user;
        });
        System.out.println(user1);


        atomicDemo.updater.incrementAndGet(user1);

        System.out.println(user1);
    }
}
