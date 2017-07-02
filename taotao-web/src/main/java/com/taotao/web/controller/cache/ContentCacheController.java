package com.taotao.web.controller.cache;

import com.taotao.common.service.RedisService;
import com.taotao.web.service.IndexService;
import com.taotao.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by dd876799869 on 2017/6/29.
 */
@Controller
@RequestMapping("content/cache")
public class ContentCacheController {
    @Autowired
    private RedisService redisService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> deleteItemCache(){
        try {
            String key1 = IndexService.REDIS_INDEXAD1;//更新大广告
            String key2 = IndexService.REDIS_INDEXAD2;//更新小广告
            this.redisService.del(key1);
            this.redisService.del(key2);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
    }

}
