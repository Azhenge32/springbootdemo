package com.azhen.monitor;

import com.azhen.mbean.Hello;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private Hello hello;
    @RequestMapping("hello")
    public String hello() {
        return hello.getName();
    }
}
