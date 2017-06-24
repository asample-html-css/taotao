package com.taotao.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

}