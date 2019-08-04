package com.youxu.juc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo{
    private int[] nums;

    public CountDownLatchDemo(int line){
        nums = new int[line];
    }

    public void compute(String line, int row){
        String[] strings = line.split(",");
        int total = 0;
        for (String s : strings){
            total = total + Integer.parseInt(s);
        }
        nums[row] = total;
        System.out.println(Thread.currentThread().getName() + "执行计算任务：" + line + " 计算结果为:" + total);
    }

    public void sum(){
        int total = 0;
        for (int i = 0; i < nums.length; i++){
            total += nums[i];
        }
        System.out.println("总的计算结果为：" + total);
    }

    public static void main(String[] args) {

        List<String> contents = readFile();

        CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo(contents.size());

        CountDownLatch countDownLatch = new CountDownLatch(contents.size());

        for(int i = 0 ; i < contents.size(); i++){
            int finalI = i;
            new Thread(() -> {
                countDownLatchDemo.compute(contents.get(finalI), finalI);
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatchDemo.sum();
    }

    private static List<String> readFile() {
        String filePath = CountDownLatchDemo.class.getClassLoader().getResource("nums.txt").getPath();
        List<String> contents = new ArrayList<>();
        String line = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {

            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null){
                contents.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contents;
    }
}
