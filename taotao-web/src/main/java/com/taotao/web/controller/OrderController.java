package com.taotao.web.controller;


import com.taotao.web.bean.Item;
import com.taotao.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yangdongan on 2017/7/14 0006.
 */
@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ModelAndView toOrder(@PathVariable("itemId") Long itemId){
        ModelAndView mv = new ModelAndView("order");
        //商品基本信息
        Item item = itemService.queryItemById(itemId);
        mv.addObject("item",item);
        return mv;
    }
}