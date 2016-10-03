package org.webonise.springboot.waitandnotify;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class WorkerThread implements Runnable {

    private final static Logger logger = Logger.getLogger(org.webonise.springboot.threadinterruption.WorkerThread.class.getName());

    @Autowired
    private SharedItem sharedItem;

    @Autowired
    private Scanner scanner;

    @Override
    public void run() {
        synchronized (sharedItem) {

            logger.info("In worker thread. Launched. Starting its job ... ");
            sharedItem.setEntity(0);
            logger.info("In worked thread. SharedItem changed.");
            logger.info("Press enter to notify main thread :");
            scanner.nextLine();
            logger.info("In worked thread. Finished its job. Notifying main thread and exiting ...");
            sharedItem.notify();
        }
    }
}
