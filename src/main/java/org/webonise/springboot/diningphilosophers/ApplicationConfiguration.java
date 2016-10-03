package org.webonise.springboot.diningphilosophers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public Philosopher[] philosophers() {
        Philosopher[] philosophers = new Philosopher[Application.NUMBER_OF_PHILOSOPHERS];
        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher();
        }

        return philosophers;
    }

    @Bean
    public ReentrantLock[] reentrantLocks() {
        ReentrantLock[] reentrantLocks = new ReentrantLock[Application.NUMBER_OF_PHILOSOPHERS];
        for (int i = 0; i < reentrantLocks.length; i++) {
            reentrantLocks[i] = reentrantLock();
        }

        return reentrantLocks;
    }

    @Bean
    @Scope("prototype")
    public ReentrantLock reentrantLock() {
        return new ReentrantLock();
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(Application.NUMBER_OF_PHILOSOPHERS);
    }
}

