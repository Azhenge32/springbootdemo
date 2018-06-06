package com.azhen.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author Azhen
 * @date 2018/04/05
 */
@Component
public class Receiver {
    @JmsListener(destination = "my-destination")
    public void receiveMessage(String message) {
        System.out.println("接收到： <" + message + ">");
    }
}
