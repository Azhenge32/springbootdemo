package com.azhen.async;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Azhen
 * @date 2018/03/30
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
        AsyncTaskService asyncTaskService =
                context.getBean(AsyncTaskService.class);

        for (int i = 0; i < 10; i ++) {
            asyncTaskService.executeAsyncTask(i);
            asyncTaskService.executeAsyncTaskPlus(i);
        }
        context.close();
    }
}
