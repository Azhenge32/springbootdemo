package com.azhen.async.controller;

import com.azhen.async.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    TestService testService;

    @RequestMapping("/sync")
    public String sync() {
        log.info("main start");
        testService.sync();
        log.info("main end");
        return "success";
    }

    @RequestMapping("/async")
    public String async() {
        log.info("main start");
        testService.async();
        log.info("main end");
        return "success";
    }

    @RequestMapping("/callable")
    public Callable<String> callable() {
        log.info("main start");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() {
                log.info("thread start");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread end");
                return "success";
            }
        };
        log.info("main end");
        return result;
    }

    private DeferredResult<String> deferredResult = new DeferredResult<>();

    /**
     * 返回DeferredResult对象
     *
     * @return
     */
    @RequestMapping("/testDeferredResult")
    public DeferredResult<String> testDeferredResult() {
        return deferredResult;
    }

    /**
     * 对DeferredResult的结果进行设置
     * @return
     */
    @RequestMapping("/setDeferredResult")
    public String setDeferredResult() {
        deferredResult.setResult("Test result!");
        return "succeed";
    }
}
