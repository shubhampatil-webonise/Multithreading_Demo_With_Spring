package org.webonise.springboot.concurrentmodificationresolution;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    @Bean
    public CopyOnWriteArrayList<Integer> list() {
        return new CopyOnWriteArrayList<Integer>();
    }
}
