package org.webonise.springboot.producerconsumer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

@Component
@Scope("prototype")
public class Consumer implements Runnable {
    private final Queue<Integer> queue;
    private static final Logger logger = Logger.getLogger(Consumer.class.getName());

    @Autowired
    Consumer(LinkedList<Integer> linkedList) {
        this.queue = linkedList;
    }

    @Override
    public void run() {
        while (true) {
            fetchResourceFromQueue();
        }
    }

    private void fetchResourceFromQueue() {
        boolean isResourceSet = false;
        int resource = 0;

        synchronized (queue) {
            if (!queue.isEmpty()) {
                resource = queue.remove();
                isResourceSet = true;
                queue.notify();
            } else {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        if (isResourceSet) {
            processResource(resource);
        }
    }

    private void processResource(int resource) {
        String threadName = Thread.currentThread().getName();
        logger.info(threadName + " processing resource :" + resource);

        try {
            Thread.sleep((new Random().nextInt(5)) * 1000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
}
