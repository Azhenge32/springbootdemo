package com.azhen.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Azhen
 * @date 2018/03/30
 */
@SpringBootApplication
@EnableAsync
public class Main {
    public static void main(String[] args) {
       /* AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
        AsyncTaskService asyncTaskService =
                context.getBean(AsyncTaskService.class);

        for (int i = 0; i < 10; i ++) {
            asyncTaskService.executeAsyncTask(i);
            asyncTaskService.executeAsyncTaskPlus(i);
        }
        context.close();*/
        SpringApplication.run(Main.class, args);
    }
}
