package com.taotao.cart.service;

import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.Item;
import com.taotao.cart.pojo.User;
import com.taotao.cart.thread.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dd876799869 on 2017/7/31.
 */
@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemService itemService;

    /**
     * 添加商品到购物车
     *
     * 逻辑：判断加入的商品在原有购物车中是否存在，如果存在数量相加，如果不存在，直接写入即可
     *
     * @param itemId
     */
    public void addItemToCart(Long itemId) {
        User user = UserThreadLocal.get();
        Cart record = new Cart();
        record.setItemId(itemId);
        record.setUserId(user.getId());
        Cart cart = this.cartMapper.selectOne(record);
        if (null == cart) {
            // 不存在
            cart = new Cart();
            cart.setUserId(user.getId());
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());
            // 商品的基本数据需要通过后台系统查询
            Item item = this.itemService.queryItemById(itemId);
            cart.setItemId(itemId);
            cart.setItemTitle(item.getTitle());
            cart.setItemPrice(item.getPrice());
            cart.setItemImage(StringUtils.split(item.getImage(), ',')[0]);
            cart.setNum(1); // TODO

            // 保存到数据库
            this.cartMapper.insert(cart);
        } else {
            // 存在,数量相加，默认数量为1 TODO
            cart.setNum(cart.getNum() + 1);
            cart.setUpdated(new Date());
            this.cartMapper.updateByPrimaryKey(cart);
        }
    }




}
