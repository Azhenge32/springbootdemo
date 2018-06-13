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

    private static class LikeRequestBody {
        private String photoId;
        private String userId;

        public String getPhotoId() {
            return photoId;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
    @PostMapping("/like")
    public String like(@RequestBody LikeRequestBody body) {
        System.out.println(body.photoId);
        return "点赞成功";
    }


    @RequestMapping("/unlike")
    public String unlike() {
        return "取消点赞成功";
    }
}
