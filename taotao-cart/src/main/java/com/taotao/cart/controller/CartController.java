package com.taotao.cart.controller;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.User;
import com.taotao.cart.service.CartService;
import com.taotao.cart.service.CartCookieService;
import com.taotao.cart.thread.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private CartCookieService cartCookieService;


    /**
     * 跳转到购物车列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView toCartList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("cart");
        User user = UserThreadLocal.get();
        List<Cart> cartList = null;
        if (null == user) {
            // 未登录状态  查询cookie中的购物车信息
            try {
                this.cartCookieService.queryCartList(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 登录状态
            cartList = cartService.queryCartList();
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
    public String addItemToCart(@PathVariable("itemId") Long itemId,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        User user = UserThreadLocal.get();
        if (null == user) {
            // 未登录状态 将商品保存到cookie中
            try {
                System.out.println(request.getRequestURI());
                System.out.println(request.getRequestURL());
                System.out.println(response.getOutputStream().toString());
                this.cartCookieService.addItemToCookieCart(itemId,request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 登录状态 将商品添加到购物车
            this.cartService.addItemToCart(itemId);
        }
        // 重定向到购物车列表页面
        return "redirect:/cart/list.html";
    }


    /**
     * 修改商品数量
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "update/num/{itemId}/{num}", method = RequestMethod.POST)
    public ResponseEntity<Void> updateNum(@PathVariable("itemId") Long itemId,
                                          @PathVariable("num") Integer num ,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        User user = UserThreadLocal.get();
        if (null == user) {
            // 未登录状态 修改cookie中的购物车信息
            try {
                this.cartCookieService.updateCookieCart(itemId,num,request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 登录状态 将商品添加到购物车
            this.cartService.updateNum(itemId, num);
        }

        // 重定向到购物车列表页面
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除购物车
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "delete/{itemId}", method = RequestMethod.GET)
    public String deleteCart(@PathVariable("itemId") Long itemId,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        User user = UserThreadLocal.get();
        if (null == user) {
            // 未登录状态
            try {
                this.cartCookieService.deleteCookieCart(itemId,request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 登录状态 删除
            this.cartService.deleteCart(itemId);
        }
        return "redirect:/cart/list.html";
    }


}
