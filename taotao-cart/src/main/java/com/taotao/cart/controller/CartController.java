package com.taotao.cart.controller;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.User;
import com.taotao.cart.service.CartService;
import com.taotao.cart.thread.UserThreadLocal;
import com.taotao.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by dd876799869 on 2017/7/31.
 */
@Controller
@RequestMapping("cart")
public class CartController {


    @Autowired
    private CartService cartService;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView cartList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("cart");
        User user = UserThreadLocal.get();
        List<Cart> cartList = null;
        if (null == user) {
            // 未登录状态
        } else {
            // 登录状态
        }
        mv.addObject("cartList", cartList);
        return mv;
    }

    /**
     * 加入商品到购物车
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public String addItemToCart(@PathVariable("itemId") Long itemId, HttpServletRequest request,
                                HttpServletResponse response) {

        User user = UserThreadLocal.get();
        if (null == user) {
            // 未登录状态

        } else {
            // 登录状态 将商品添加到购物车
            this.cartService.addItemToCart(itemId);

        }

        // 重定向到购物车列表页面
        return "redirect:/cart/list.html";
    }


}
