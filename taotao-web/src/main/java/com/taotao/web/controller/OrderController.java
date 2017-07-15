package com.taotao.web.controller;


import com.taotao.manage.pojo.Order;
import com.taotao.web.bean.Item;
import com.taotao.web.bean.User;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static com.taotao.web.interceptors.UserLoginInterceptors.COOKIE_NAME;

/**
 * Created by yangdongan on 2017/7/14 0006.
 */
@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    /**
     * 跳转到指定商品的确认订单结算页面
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ModelAndView toOrder(@PathVariable("itemId") Long itemId){
        ModelAndView mv = new ModelAndView("order");
        //商品基本信息
        Item item = itemService.queryItemById(itemId);
        mv.addObject("item",item);
        return mv;
    }

    /**
     * 提交订单
     * @param order
     * @return
     */
    @RequestMapping(value = "submit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> submit(Order order,
                                     @CookieValue(COOKIE_NAME) String token){
        Map<String,Object> result = new HashMap<String, Object>();
        //首先从cookie中补全用户信息
        User user = userService.queryUserByToken(token);
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        //提交订单,应该通过httpClient提交
       String orderId =  orderService.submitOrder(order);
       if (orderId == null){
           result.put("status",500);
       }else{
           result.put("status",200);
           result.put("data",orderId);
       }
        return result;
    }

}