package com.taotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.service.ApiService;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.web.bean.Cart;
import com.taotao.web.bean.Item;
import com.taotao.web.bean.User;
import com.taotao.web.thread.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by dd876799869 on 2017/6/24.
 */
@Service
public class CartService {

    @Autowired
    private ApiService apiService;

    @Value("${CART_TAOTAO_URL}")
    public String CART_TAOTAO_URL;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 查询用户购物车信息cart/api/userId
     * @return
     */
    public List<Cart> queryCartList() {
        String url =CART_TAOTAO_URL +  "/service/cart/api/" + UserThreadLocal.get().getId();
        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)){
                return null;
            }
            return MAPPER.readValue(jsonData,MAPPER.getTypeFactory().constructCollectionType(List.class,Cart.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}