package com.azhen.redis.like;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/counter")
public class LikeController {
    @Resource
    private LikeService likeService;

    @PostMapping("/like")
    public String like(@RequestBody LikeRequestBody body) {
        //在likeKey对应的集合中加入当前用户的id
        long likeCount = likeService.like(body.getUserId(), EntityType.ENTITY_NEWS, body.getArticleId());

        // 操作MySQL, 资讯上更新点赞数
        // likeService.updateLikeCount(newsId, (int)likeCount);
        return "点赞成功";
    }


    @RequestMapping("/unlike")
    public String unlike(@RequestBody LikeRequestBody body) {
//在disLikeKey对应的集合中加入当前用户
        long likeCount = likeService.disLike(body.getUserId(), EntityType.ENTITY_NEWS, body.getArticleId());
        if(likeCount <= 0){
            likeCount = 0;
        }

        // 操作MySQL, 资讯上更新点赞数
        // likeService.updateLikeCount(newsId, (int)likeCount);
        return "取消点赞成功";
    }

 /*   @RequestMapping("/article")
    public String articleLIke(@RequestBody LikeRequestBody body) {
        // 操作MySQL, 资讯上更新点赞数
        // likeService.updateLikeCount(newsId, (int)likeCount);
        return "取消点赞成功";
    }*/
}
