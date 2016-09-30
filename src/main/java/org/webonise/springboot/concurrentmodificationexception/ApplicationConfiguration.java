package org.webonise.springboot.concurrentmodificationexception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    @Bean
    public ArrayList<Integer> list() {
        return new ArrayList<Integer>();
    }
}
