package org.webonise.springboot.diningphilosophers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class Application {
    public static final int NUMBER_OF_PHILOSOPHERS = 5;
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    private final Philosopher[] philosophers;
    private final ReentrantLock[] chopsticks;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    Application(@Qualifier("philosophers") Philosopher[] philosophers, @Qualifier("reentrantLocks") ReentrantLock[] reentrantLocks) {
        this.philosophers = philosophers;
        this.chopsticks = reentrantLocks;
    }

    public void start() {
        startPhilosopherThreads();
    }

    private void startPhilosopherThreads() {
        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            philosophers[i].setId(i);
            philosophers[i].setLeftChopstick(chopsticks[i]);
            philosophers[i].setRightChopstick(chopsticks[(i + 1) % NUMBER_OF_PHILOSOPHERS]);

            threadPoolExecutor.execute(philosophers[i]);
        }

        try {
            Thread.sleep(10000);
            threadPoolExecutor.shutdown();
            for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
                philosophers[i].finishEating();
            }
            threadPoolExecutor.awaitTermination(20, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
}
