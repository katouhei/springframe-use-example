package com.jx.nc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:appcontext-*.xml");
        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        System.out.println("14.Bean working, message = " + obj.getMessage());
        //((ClassPathXmlApplicationContext)context).refresh();
        ((ClassPathXmlApplicationContext) context).close();
    }
}
