package com.taotao.web.mq.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dd876799869 on 2017/7/30.
 */
public class ItemMQHandler {

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void execute(String msg) {
        try {
            JsonNode jsonNode = MAPPER.readTree(msg);
            Long itemId = jsonNode.get("itemId").asLong();
            String key = ItemService.REDIS_ITEM + itemId;
            this.redisService.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
