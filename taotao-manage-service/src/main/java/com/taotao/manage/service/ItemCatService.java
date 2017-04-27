package com.taotao.manage.service;

import java.util.List;

import com.taotao.manage.mapper.ItemCatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taotao.manage.pojo.ItemCat;

@Service
public class ItemCatService {

     @Autowired
     private ItemCatMapper itemCatMapper;


    public List<ItemCat> queryItemCatList(Long parentId) {
        // 设置查询条件
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        return itemCatMapper.select(itemCat);
    }


}
