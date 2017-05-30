package com.taotao.manage.service;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dd876799869 on 2017/5/29.
 */
@Service
public class ItemService extends BaseService<Item> {
    @Autowired
    private ItemDescService itemDescService;


    public boolean saveItem(Item item, String desc) {
        item.setStatus(1);//设置初始值
        item.setId(null);//防止id注入
       Integer count1 =  super.save(item);

        //保存ItemDesc
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer count2 =  itemDescService.save(itemDesc);
        return count1.intValue() == 1 && count2.intValue() == 1;
    }
}
