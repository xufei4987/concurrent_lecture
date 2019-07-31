package com.youxu.threadCommunication;

public class ABCDemo {

    private int signal = 1;

    public synchronized void a(){

        while (signal != 1){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("a");
        signal = 2;
        notifyAll();
    }

    public synchronized void b(){
        while (signal != 2){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("b");
        signal = 3;
        notifyAll();
    }

    public synchronized void c(){
        while (signal != 3){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("c");
        signal = 1;
        notifyAll();
    }

    public static void main(String[] args) {
        ABCDemo abcDemo = new ABCDemo();
        TaskA taskA = new TaskA(abcDemo);
        TaskB taskB = new TaskB(abcDemo);
        TaskC taskC = new TaskC(abcDemo);
        new Thread(taskA).start();
        new Thread(taskB).start();
        new Thread(taskC).start();
    }

}

class TaskA implements Runnable{
    private ABCDemo abcDemo;

    public TaskA(ABCDemo abcDemo) {
        this.abcDemo = abcDemo;
    }

    @Override
    public void run() {
        while (true){
            abcDemo.a();
        }
    }
}

class TaskB implements Runnable{
    private ABCDemo abcDemo;

    public TaskB(ABCDemo abcDemo) {
        this.abcDemo = abcDemo;
    }

    @Override
    public void run() {
        while (true){
            abcDemo.b();
        }
    }
}

class TaskC implements Runnable{
    private ABCDemo abcDemo;

    public TaskC(ABCDemo abcDemo) {
        this.abcDemo = abcDemo;
    }

    @Override
    public void run() {
        while (true){
            abcDemo.c();
        }
    }
}