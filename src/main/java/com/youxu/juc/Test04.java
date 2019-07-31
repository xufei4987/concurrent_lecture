package com.youxu.juc;

import java.util.Timer;
import java.util.TimerTask;

public class Test04 {
    public static void main(String[] args) {

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("task is run");
            }
        }, 0, 1000);

    }
}
