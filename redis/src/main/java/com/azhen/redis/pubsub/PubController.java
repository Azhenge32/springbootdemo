package com.azhen.redis.pubsub;

import com.azhen.redis.core.RedisCache;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pubsub")
public class PubController {
    @Resource
    private RedisCache redisCache;

    @PostMapping("/pub")
    public String pub() {
        Map<String, String> message = new HashMap<>();
        message.put("type", "delete");
        redisCache.publisher(message);
        return "消息发布成功";
    }
}
