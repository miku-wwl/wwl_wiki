package com.jiawa.wiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WikiApplication {

    public static void main(String[] args) {
        System.out.println("hello world!");
        SpringApplication.run(WikiApplication.class, args);
    }

}
