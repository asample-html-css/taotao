package com.taotao.web.controller;


import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.web.bean.Item;
import com.taotao.web.service.IndexService;
import com.taotao.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yangdongan on 2017/6/6 0006.
 */
@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ModelAndView item(@PathVariable("itemId") Long itemId){
        ModelAndView mv = new ModelAndView("item");

        //商品基本信息
        Item item = itemService.queryItemById(itemId);
        mv.addObject("item",item);

        //商品详信息
        ItemDesc itemDesc = itemService.queryItemDescById(itemId);
        mv.addObject("itemDesc",itemDesc);

        //商品规格参数
        String itemParamItem = itemService.queryItemParamItemById(itemId);
        mv.addObject("itemParamItem",itemParamItem);
        return mv;
    }
}