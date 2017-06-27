package com.taotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.web.bean.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by dd876799869 on 2017/6/24.
 */
@Service
public class ItemService {

    @Value("${ITEM_BASE_URL}")
    public String ITEM_BASE_URL;

    @Value("${ITEM_DESC_BASE_URL}")
    public String ITEM_DESC_BASE_URL;

    @Value("${ITEM_PARAM_ITEM_BASE_URL}")
    public String ITEM_PARAM_ITEM_BASE_URL;


    @Autowired
    private ApiService apiService;

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    private static String REDIS_ITEM = "TAOTAO_WEB_ITEM";

    private static String REDIS_ITEM_DESC = "TAOTAO_WEB_ITEM_DESC";

    private static String REDIS_ITEM_PARAM_ITEM = "TAOTAO_WEB_ITEM_PARAM_ITEM";

    private static Integer REDIS_ITEM_TIME = 60 * 60 * 24 ;//保存一天


    /**
     * 查询商品基本信息
     * @return
     */
    public Item queryItemById(Long itemId){
        //首先到缓存中命中
        String jsonData =redisService.getCacheString(REDIS_ITEM + itemId);
        if (StringUtils.isNotEmpty(jsonData)){
            try {
                return MAPPER.readValue(jsonData,Item.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //获取原生json数据
            String url = ITEM_BASE_URL + "/"+ itemId;
            //此处返回的是json数据
            jsonData =  apiService.doGet(url);
            if (jsonData==null){
                return null;
            }
            //在返回之前，将数据保存到缓存中
            try {
                redisService.set(REDIS_ITEM + itemId, jsonData, REDIS_ITEM_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //直接将对象返回
            return MAPPER.readValue(jsonData,Item.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 查询商品详细信息
     * @return
     */
    public ItemDesc queryItemDescById(Long itemId) {
        //首先到缓存中命中
        String jsonData =redisService.getCacheString(REDIS_ITEM_DESC + itemId);
        if (StringUtils.isNotEmpty(jsonData)){
            try {
                return MAPPER.readValue(jsonData,ItemDesc.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //获取原生json数据
            String url = ITEM_DESC_BASE_URL + "/"+ itemId;
            //此处返回的是json数据
            jsonData =  apiService.doGet(url);
            if (jsonData==null){
                return null;
            }
            //在返回之前，将数据保存到缓存中
            try {
                redisService.set(REDIS_ITEM_DESC + itemId, jsonData, REDIS_ITEM_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //直接将对象返回
            return MAPPER.readValue(jsonData,ItemDesc.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 查询商品规格参数
     * @return
     */
    public String queryItemParamItemById(Long itemId) {

        //首先到缓存中命中
        String jsonData =redisService.getCacheString(REDIS_ITEM_PARAM_ITEM + itemId);
        if (StringUtils.isNotEmpty(jsonData)){
            return jsonData;
        }

        try {
            //获取原生json数据
            String url = ITEM_PARAM_ITEM_BASE_URL + "/"+ itemId;
            //此处返回的是json数据
            jsonData =  apiService.doGet(url);
            if (jsonData==null){
                return null;
            }
            // 将json数据反序列
            ItemParamItem itemParamItem = MAPPER.readValue(jsonData, ItemParamItem.class);
            String paramData = itemParamItem.getParamData();

            ArrayNode arrayNode = (ArrayNode) MAPPER.readTree(paramData);

            StringBuilder sb = new StringBuilder();
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"><tbody>");

            for (JsonNode param : arrayNode) {
                sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">" + param.get("group").asText()
                        + "</th></tr>");
                ArrayNode params = (ArrayNode) param.get("params");
                for (JsonNode p : params) {
                    sb.append("<tr><td class=\"tdTitle\">" + p.get("k").asText() + "</td><td>"
                            + p.get("v").asText() + "</td></tr>");
                }
            }
            sb.append("</tbody></table>");
            jsonData = sb.toString();
            //在返回之前，将数据保存到缓存中
            try {
                redisService.set(REDIS_ITEM_PARAM_ITEM + itemId, jsonData, REDIS_ITEM_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}