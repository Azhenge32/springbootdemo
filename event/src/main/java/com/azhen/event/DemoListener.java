package com.azhen.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Azhen
 * @date 2018/03/30
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {
    @Override
    public void onApplicationEvent(DemoEvent demoEvent) {
        String msg = demoEvent.getMsg();
        System.out.println("我(bean-demoListener)接受倒了bean-demoPublisher发布的消息：" + msg);
    }
}
