package com.taotao.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.search.bean.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by dd876799869 on 2017/7/30.
 */

@Service
public class ItemMQService {

    @Value("${ITEM_BASE_URL}")
    public String ITEM_BASE_URL;

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Item queryItemById(Long itemId){
        try {
            String url = ITEM_BASE_URL + "/"+ itemId;
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isNotEmpty(jsonData)) {
                return MAPPER.readValue(jsonData, Item.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
