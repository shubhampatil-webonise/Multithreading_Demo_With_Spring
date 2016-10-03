package org.webonise.springboot.producerconsumer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class Application {
    private final Queue<Integer> queue;
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private Producer producer;

    @Autowired
    private Consumer firstConsumer;

    @Autowired
    private Consumer secondConsumer;

    @Autowired
    public Application(final LinkedList<Integer> linkedList) {
        this.queue = linkedList;
    }
    
    public void start() {
        threadPoolExecutor.execute(producer);
        threadPoolExecutor.execute(firstConsumer);
        threadPoolExecutor.execute(secondConsumer);

        try {
            threadPoolExecutor.shutdown();
            threadPoolExecutor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
}
