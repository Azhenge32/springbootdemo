package com.azhen.redis.lock;

import com.azhen.redis.core.lock.RedisLockServiceTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.*;

@RestController
@RequestMapping("/lock")
public class LockController {
    @Resource
    private RedisLockServiceTemplate redisLockServiceTemplate;
    @RequestMapping("/test/{num}")
    public String test(@PathVariable("num") Integer num) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(num, num,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10_000; i ++) {
            int val = i;
            executorService.execute(() -> {
                redisLockServiceTemplate.service("codehole", () -> {
                    System.out.println("业务代码");
                    return null;
                });
            });
        }
        long taskCount = 0;
        while ((taskCount = executorService.getCompletedTaskCount()) != 10_000) {
            System.out.println("taskCount : " + taskCount);
        }
        System.out.printf("Cost Time : %d\n", System.currentTimeMillis() - startTime);
       return null;
    }
}
