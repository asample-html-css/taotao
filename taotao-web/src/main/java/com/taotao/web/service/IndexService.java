package com.taotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dd876799869 on 2017/6/10.
 */
@Service
public class IndexService   {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private ApiService apiService;

    /**
     * 查询大广告
     * @return
     */
    public String queryIndexAd1() {

        try {
            //获取原生json数据
            String url = "http://manage.taotao.com/rest/content?categoryId=66&page=1&rows=6";
            //此处返回的是json数据
            String jsonData =  apiService.doGet(url);
            if (jsonData==null){
                return null;
            }

            //解析jsonData 组织成前端所需数据结构
            //将json数据反序列化成jsonNode
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            //将数据封装成数组  拿到返回数据中的rows
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");

            List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();//6条广告封装的容器

            for (JsonNode row :rows ) {//对rows中的每个节点JsonNode进行遍历
                Map<String,Object> map = new LinkedHashMap<String, Object>();

                map.put("srcB",row.get("pic").asText());
                map.put("height",240);
                map.put("alt",row.get("title").asText());
                map.put("width",670);
                map.put("src",row.get("pic").asText());
                map.put("widthB",550);
                map.put("href",row.get("url").asText());
                map.put("heightB",240);

                result.add(map);
            }
           // return  result.toString();简单的toString会生成 = 号
            return MAPPER.writeValueAsString(result);//将json对象序列化成json字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
