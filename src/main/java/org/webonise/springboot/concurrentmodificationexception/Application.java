package org.webonise.springboot.concurrentmodificationexception;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class Application {
    private final List<Integer> list;
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private WorkerThread workerThread;

    @Autowired
    public Application(ArrayList<Integer> list) {
        this.list = list;
    }

    public void start() {
        populateList();
        generateConcurrentModificationException();
    }

    private void populateList() {
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
    }

    private void generateConcurrentModificationException() {
        launchThread();
        iterateList();
        waitForThreadToJoin();
        logger.info("Main thread is exiting now !");
    }

    private void launchThread() {
        workerThread.setList(list);
        threadPoolExecutor.execute(workerThread);
    }

    private void iterateList() {
        Iterator iterator = list.iterator();

        try {
            while (iterator.hasNext()) {
                logger.info(iterator.next().toString());
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitForThreadToJoin() {
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(20, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
