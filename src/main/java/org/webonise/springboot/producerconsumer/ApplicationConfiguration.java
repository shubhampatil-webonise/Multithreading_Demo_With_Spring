package org.webonise.springboot.producerconsumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public LinkedList<Integer> linkedList() {
        return new LinkedList<Integer>();
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }
}
