package com.azhen.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Azhen
 * @date 2018/03/30
 */
public class DemoEvent extends ApplicationEvent {
    private String msg;
    public DemoEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
