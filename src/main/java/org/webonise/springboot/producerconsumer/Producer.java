package org.webonise.springboot.producerconsumer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
@Scope("prototype")
public class Producer implements Runnable {
    private final Queue<Integer> queue;
    private static final Logger logger = Logger.getLogger(Producer.class.getName());

    @Autowired
    Producer(LinkedList<Integer> linkedList) {
        this.queue = linkedList;
    }

    @Override
    public void run() {
        int resource = 0;

        while (true) {
            putResourceIntoQueue(resource++);
        }
    }

    private void putResourceIntoQueue(int resource) {

        String threadName = Thread.currentThread().getName();

        synchronized (queue) {
            queue.add(resource);
            logger.info(threadName + " adding resource : " + resource);
            queue.notifyAll();

            try {
                queue.wait(10000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
