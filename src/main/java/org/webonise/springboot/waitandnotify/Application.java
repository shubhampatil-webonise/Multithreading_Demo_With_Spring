package org.webonise.springboot.waitandnotify;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    @Autowired
    private SharedItem sharedItem;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private WorkerThread workerThread;

    public void start() {
        threadPoolExecutor.execute(workerThread);
        logger.info("In main thread. Launching worker thread ...");

        synchronized (sharedItem) {
            try {
                logger.info("In main thread. Waiting for notification from worker thread ...");
                sharedItem.wait();
                threadPoolExecutor.shutdown();
                threadPoolExecutor.awaitTermination(20, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }

        logger.info("In main thread. Notification received from worker thread. Exiting ...");
    }
}
