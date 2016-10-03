package org.webonise.springboot.threadinterruption;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public Runnable runnable() {
        return new WorkerThread();
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }
}
