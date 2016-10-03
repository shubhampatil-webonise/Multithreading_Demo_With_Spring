package org.webonise.springboot.concurrentmodificationexception;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ListIterator;

@Component
@Scope("prototype")
public class WorkerThread implements Runnable {
    private List<Integer> list;
    private static final Logger logger = Logger.getLogger(WorkerThread.class.getName());
    
    public void setList(final List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        ListIterator iterator = list.listIterator(list.size());
        while (iterator.hasPrevious()) {
            iterator.previous();
            iterator.remove();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
