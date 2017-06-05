package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.pojo.ItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dd876799869 on 2017/5/29.
 */
@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {

    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    /**
     * 保存商品规格参数
     * @param itemParams
     * @return
     */
    public Integer updateItemParamItem(Long ItemId,String itemParams) {
        //更新数据
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setParamData(itemParams);
        itemParamItem.setUpdated(new Date());

        //更新条件
        Example example  = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId",ItemId);

       return itemParamItemMapper.updateByExampleSelective(itemParamItem,example);

    }
}
