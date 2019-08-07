package com.youxu.jucContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/8/5 16:17
 * 写时复制集合：用于读多写少的场景
 * 优点：适合使用在读操作远远大于写操作的场景里，比如缓存。发生修改时候做copy，新老版本分离，
 * 保证读的高性能，适用于以读为主的情况。
 * 缺点：CopyOnWriteArrayList采用“写入时复制”策略，对容器的写操作将导致的容器中基本数组的复制，
 * 性能开销较大。所以在有写操作的情况下，CopyOnWriteArrayList性能不佳，而且如果容器容量较大的话容易造成溢出。
 * 普通ArrayList在遍历操作时并发进行元素的修改操作会引起ConcurrentModificationException，所以出现了CopyOnWriteArrayList
 **/

public class CopyOnWriteArrayListDemo {

    private static class ReadTask implements Runnable {
        List<String> list;

        public ReadTask(List<String> list) {
            this.list = list;
        }

        public void run() {
            for (String str : list) {
                System.out.println(str);
            }
        }
    }

    private static class WriteTask implements Runnable {
        List<String> list;
        int index;

        public WriteTask(List<String> list, int index) {
            this.list = list;
            this.index = index;
        }

        public void run() {
            list.remove(index);
            list.add(index, "write_" + index);
        }
    }

    public static void main(String[] args) {
        final int NUM = 10;
//        List<String> list = new ArrayList<String>();
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < NUM; i++) {
            list.add("main_" + i);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(NUM);
        for (int i = 0; i < NUM; i++) {
            executorService.execute(new ReadTask(list));
            executorService.execute(new WriteTask(list, i));
        }
        executorService.shutdown();
    }


}
