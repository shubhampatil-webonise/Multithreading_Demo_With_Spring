package org.webonise.springboot.diningphilosophers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class Philosopher implements Runnable {

    private int philosopherId;
    private ReentrantLock leftChopstick;
    private ReentrantLock rightChopstick;
    private boolean hasFinishedEating;
    private static final Logger logger = Logger.getLogger(Philosopher.class.getName());

    Philosopher() {
        this.hasFinishedEating = false;
    }

    public void setId(int philosopherId) {
        this.philosopherId = philosopherId;
    }

    public void setLeftChopstick(ReentrantLock leftChopstick) {
        this.leftChopstick = leftChopstick;
    }

    public void setRightChopstick(ReentrantLock rightChopstick) {
        this.rightChopstick = rightChopstick;
    }

    @Override
    public void run() {
        try {
            while (!hasFinishedEating) {
                think();

                if (acquireLeftChopstick()) {
                    if (acquireRightChopstick()) {
                        eat();
                        releaseRightChopstick();
                    }
                    releaseLeftChopstick();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void finishEating() {
        hasFinishedEating = true;
        logger.info("Philosopher " + philosopherId + " will now finish eating.");
    }

    private void think() throws InterruptedException {
        logger.info("Philosopher " + philosopherId + " is now thinking.");
        Thread.sleep(new Random().nextInt(1000));
    }

    private void eat() throws InterruptedException {
        logger.info("Philosopher " + philosopherId + " is now eating.");
        Thread.sleep(new Random().nextInt(1000));
    }

    private boolean acquireLeftChopstick() {
        if (leftChopstick.tryLock()) {
            logger.info("Philosopher " + philosopherId + " has picked up left chopstick.");
            return true;
        }
        return false;
    }

    private boolean acquireRightChopstick() {
        if (rightChopstick.tryLock()) {
            logger.info("Philosopher " + philosopherId + " has picked up right chopstick.");
            return true;
        }
        return false;
    }

    private void releaseLeftChopstick() {
        if (leftChopstick.isHeldByCurrentThread()) {
            leftChopstick.unlock();
            logger.info("Philosopher " + philosopherId + " has put down left chopstick.");
        }
    }

    private void releaseRightChopstick() {
        if (rightChopstick.isHeldByCurrentThread()) {
            rightChopstick.unlock();
            logger.info("Philosopher " + philosopherId + " has put down right chopstick.");
        }
    }
}
