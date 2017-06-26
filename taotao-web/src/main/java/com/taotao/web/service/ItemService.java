package com.taotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.web.bean.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 查询商品基本信息
     * @return
     */
    public Item queryItemById(Long itemId){

        try {
            //获取原生json数据
            String url = ITEM_BASE_URL + "/"+ itemId;
            //此处返回的是json数据
            String jsonData =  apiService.doGet(url);
            if (jsonData==null){
                return null;
            }
            //直接将对象返回
            return MAPPER.readValue(jsonData,Item.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public ItemDesc queryItemDescById(Long itemId) {

        try {
            //获取原生json数据
            String url = ITEM_DESC_BASE_URL + "/"+ itemId;
            //此处返回的是json数据
            String jsonData =  apiService.doGet(url);
            if (jsonData==null){
                return null;
            }
            //直接将对象返回
            return MAPPER.readValue(jsonData,ItemDesc.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public String queryItemParamItemById(Long itemId) {

        try {
            //获取原生json数据
            String url = ITEM_PARAM_ITEM_BASE_URL + "/"+ itemId;
            //此处返回的是json数据
            String jsonData =  apiService.doGet(url);
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
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}