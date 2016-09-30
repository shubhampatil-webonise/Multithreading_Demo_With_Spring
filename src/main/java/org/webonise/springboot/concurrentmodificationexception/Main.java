package org.webonise.springboot.concurrentmodificationexception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    private Application application;

    @Override
    public void run(String... strings) throws Exception {
        application.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
