package com.azhen.redis.like;


import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikeService {
    @Resource
    private com.azhen.redis.core.RedisCache redisCache;


}
