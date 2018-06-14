package com.azhen.redis.like;


import com.azhen.redis.core.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikeService {
    @Resource
    private RedisCache redisCache;

    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";

    /**
     * 产生key:如在newsId为2上的咨询点赞后会产生key: LIKE:ENTITY_NEWS:2
     * @param entityId
     * @param entityType
     * @return
     */
    public static String getLikeKey(String entityId, String entityType){
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    /**
     * 取消赞:如在newsId为2上的资讯取消点赞后会产生key: DISLIKE:ENTITY_NEWS:2
     * @param entityId
     * @param entityType
     * @return
     */
    public static String getDisLikeKey(String entityId, String entityType){
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    /**
     * 点赞：即当前用户点赞后，被点赞用户的like集合中就会加上一个该点赞的用户信息
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public long like(String userId, String entityType, String entityId){
        //在当前news上点赞后获取key:   LIKE:ENTITY_NEWS:2
        String likeKey = getLikeKey(entityId, entityType);
        //在喜欢集合中添加当前操作用户的userId(即当前用户点赞后，被点赞用户的like集合中就会加上一个点赞的用户信息)
        redisCache.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = getDisLikeKey(entityId, entityType);
        redisCache.srem(disLikeKey, String.valueOf(userId));

        //返回点赞数量
        return redisCache.scard(likeKey);
    }

    public long disLike(String userId, String entityType, String entityId){
        //在当前news上点赞后获取key:   LIKE:ENTITY_NEWS:2
        String likeKey = getLikeKey(entityId, entityType);
        //在喜欢集合中添加当前操作用户的userId(即当前用户点赞后，被点赞用户的like集合中就会加上一个点赞的用户信息)
        redisCache.srem(likeKey, String.valueOf(userId));

        String disLikeKey = getDisLikeKey(entityId, entityType);
        redisCache.sadd(disLikeKey, String.valueOf(userId));

        //返回点赞数量
        return redisCache.scard(likeKey);
    }
}
