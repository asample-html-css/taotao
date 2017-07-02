package com.taotao.web.controller.cache;

import com.taotao.common.service.RedisService;
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
@RequestMapping("itemParamItem/cache")
public class itemParamItemCacheController {
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "{itemId}",method = RequestMethod.POST)
    public ResponseEntity<Void> deleteItemParamItemCache(@PathVariable("itemId") Long itemId){

        try {
            String key = ItemService.REDIS_ITEM_PARAM_ITEM+ itemId;
            redisService.del(key);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
    }

}
