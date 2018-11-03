package com.azhen.starter;

import com.azhen.service.StarterService;
import org.springframework.beans.factory.annotation.Autowired;

public class Test {
    @Autowired
    private StarterService starterService;

    @Test
    public void starterTest() {
        String[] splitArray = starterService.split(",");
        System.out.println(splitArray);
    }
}
