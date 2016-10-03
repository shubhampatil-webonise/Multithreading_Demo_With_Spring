package org.webonise.springboot.threadinterruption;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class WorkerThread implements Runnable {

    private final static Logger logger = Logger.getLogger(WorkerThread.class.getName());

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
                logger.info("Worker thread is running.");
            } catch (InterruptedException e) {
                logger.info("Interrupting worker thread.");
                Thread.currentThread().interrupt();
            }
        }
    }
}
