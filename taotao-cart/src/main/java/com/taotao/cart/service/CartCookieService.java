package com.taotao.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.Item;
import com.taotao.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dd876799869 on 2017/7/30.
 */

@Service
public class CartCookieService {

    private static final String COOKIE_NAME = "TT_CART";
    private static final Integer COOKIE_TIME = 60 * 60 * 24 * 30 * 12;


    @Autowired
    private ItemService itemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * 将购物车信息保存到cookie中
     *
     * @param itemId
     * @param request
     * @param response
     */
    public void addItemToCookieCart(Long itemId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 判断该商品在购物车中是否存在，如果存在数量相加，不存在，直接添加
        List<Cart> carts = this.queryCartList(request);
        Cart cart = null;
        for (Cart c : carts) {
            if (c.getItemId().longValue() == itemId.longValue()) {
                cart = c;//在这个循环中只会选出一条itemId相符的
                break;
            }
        }

        if (cart == null) {
            // 不存在
            cart = new Cart();
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());
            cart.setItemId(itemId);
            cart.setNum(1); // TODO 默认为1
            Item item = this.itemService.queryItemById(itemId);
            cart.setItemTitle(item.getTitle());
            cart.setItemPrice(item.getPrice());
            cart.setItemImage(item.getImages()[0]);
            carts.add(cart);
        } else {
            // 存在
            cart.setNum(cart.getNum() + 1); // TODO 默认为1
            cart.setUpdated(new Date());
        }
        //将购物车信息保存到cookie中
        this.aveCartsToCookie(request, response, carts);
    }

    //将购物车信息保存到cookie中
    private void aveCartsToCookie(HttpServletRequest request, HttpServletResponse response, List<Cart> carts) {
        try {
            // 将购物车数据写入到cookie中
            CookieUtils.setCookie(request, response, COOKIE_NAME, MAPPER.writeValueAsString(carts),
                    COOKIE_TIME, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询cookie中的购物车信息
     */
    public List<Cart> queryCartList(HttpServletRequest request) throws Exception {
        //首先查询cookie中的购物车列表
        String jsonData = CookieUtils.getCookieValue(request, COOKIE_NAME,true);
        //查询cookie中是否有购物车信息
        List<Cart> carts = null;
        if (StringUtils.isEmpty(jsonData)) {//没有购物城信息
            carts =  new ArrayList<Cart>(0);
        } else {
                //cookie中有购物车信息
            carts =  MAPPER.readValue(jsonData,
                        MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
        }
        return  carts;
    }

    /**
     * 更新cookie中的购物车信息
     * @param itemId
     * @param num
     * @param request
     * @param response
     */
    public void updateCookieCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 判断该商品在购物车中是否存在，如果存在数量相加，不存在，直接添加
        List<Cart> carts = this.queryCartList(request);
        Cart cart = null;
        for (Cart c : carts) {
            if (c.getItemId().longValue() == itemId.longValue()) {
                cart.setNum(cart.getNum() + 1); // TODO 默认为1
                cart.setUpdated(new Date());
                break;
            }
        }
        //将购物车信息保存到cookie中
        this.aveCartsToCookie(request, response, carts);
    }

    /**
     * 删除cookie中的购物车信息
     * @param itemId
     * @param request
     * @param response
     */
    public void deleteCookieCart(Long itemId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 判断该商品在购物车中是否存在，如果存在数量相加，不存在，直接添加
        List<Cart> carts = this.queryCartList(request);
        for (Cart c : carts) {
            if (c.getItemId().longValue() == itemId.longValue()) {
                carts.remove(c);
                break;
            }
        }
        //将购物车信息保存到cookie中
        this.aveCartsToCookie(request, response, carts);
    }
}
