package org.webonise.springboot.threadinterruption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class Application {

    @Autowired
    private Runnable runnable;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;


    public void start() {
        threadPoolExecutor.execute(runnable);

        try {
            Thread.sleep(10000);
            threadPoolExecutor.shutdownNow();
            threadPoolExecutor.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
