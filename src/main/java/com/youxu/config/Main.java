package com.youxu.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfig.class);
        MyService myService = ac.getBean(MyService.class);
        myService.service1();
        myService.service2();
    }
}
