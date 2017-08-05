package com.taotao.cart.service;

import com.github.abel533.entity.Example;
import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.Item;
import com.taotao.cart.pojo.User;
import com.taotao.cart.thread.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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


    /**
     * 查询购物车列表
     * @return
     */
    public List<Cart> queryCartList() {
        Example example = new Example(Cart.class);
        example.setOrderByClause("created desc");
        example.createCriteria().andEqualTo("userId",UserThreadLocal.get().getId());
        return this.cartMapper.selectByExample(example);
    }

    /**
     * 更新商品数量
     * @param itemId
     * @param num
     */
    public void updateNum(Long itemId, Integer num) {
        //更新条件
        Example example = new Example(Cart.class);
        example.createCriteria().andEqualTo("userId",UserThreadLocal.get().getId())
                .andEqualTo("itemId",itemId);
        //更新内容
        Cart record = new Cart();
        record.setNum(num);
        record.setItemId(itemId);
        this.cartMapper.updateByExampleSelective(record,example);

    }

    /**
     * 删除购物车商品
     * @param itemId
     * @param num
     */
    public void deleteCart(Long itemId) {

        Cart record = new Cart();
        record.setItemId(itemId);
        record.setUserId(UserThreadLocal.get().getId());

        this.cartMapper.delete(record);



    }
}
