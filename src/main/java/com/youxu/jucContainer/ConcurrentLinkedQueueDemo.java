package com.youxu.jucContainer;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/8/5 16:40
 * 添加时，tail节点并不一定指向真正的尾结点，为偶数时尾结点是tail.next()，为奇数时尾结点就是tail
 * 弹出时，head节点并不一定指向真正的头节点，为偶数时头结点是head.next()，为奇数时头结点就是head
 **/
public class ConcurrentLinkedQueueDemo {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> linkedQueue = new ConcurrentLinkedQueue<>();
        linkedQueue.offer("a");
        linkedQueue.offer("b");
        linkedQueue.offer("c");
        linkedQueue.offer("d");
        linkedQueue.offer("e");
        System.out.println(linkedQueue);
        System.out.println(linkedQueue.poll());
        System.out.println(linkedQueue.peek());
        linkedQueue.spliterator().forEachRemaining(System.out::println);
    }
}
