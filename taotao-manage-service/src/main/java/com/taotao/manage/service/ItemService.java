package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dd876799869 on 2017/5/29.
 */
@Service
public class ItemService extends BaseService<Item> {
    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemParamItemService itemParamItemService;

    /**
     * 新增商品
     * @param item
     * @param desc
     * @return
     */
    public boolean saveItem(Item item, String desc,String itemParams) {
        //保存商品
        item.setStatus(1);//设置初始值
        item.setId(null);//防止id注入
        Integer count1 =  super.save(item);

        //保存ItemDesc
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer count2 =  itemDescService.save(itemDesc);

        //保存商品参数信息
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(itemParams);
        Integer count3 =  itemParamItemService.save(itemParamItem);

        return count1.intValue() == 1 && count2.intValue() == 1 && count3.intValue() == 1 ;
    }

    /**
     * 查询商品列表
     * @return
     */
    public EasyUIResult queryItemList(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        Example example = new Example(Item.class);
        example.setOrderByClause("created desc");
        List<Item> itemlist = this.itemMapper.selectByExample(example);

        PageInfo<Item> pageInfo = new PageInfo<Item>(itemlist);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 根据cid查找cname
     * @return
     */
    public String queryCname(Long cid) {
        return "手机"+cid;
    }

    /**
     * 更新商品
     * @param item
     * @param desc
     * @return
     */
    public Boolean updateItem(Item item, String desc,String itemParams) {
        //强制设置不能更新的字段为空
        item.setStatus(null);
        item.setCreated(null);

        Integer count1 =  super.updateSelective(item);

        //保存ItemDesc
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer count2 =  itemDescService.updateSelective(itemDesc);

        //保存商品规格参数
        Integer count3 =  itemParamItemService.updateItemParamItem(item.getId(),itemParams);
        return count1.intValue() == 1 && count2.intValue() == 1 && count3.intValue() == 1;
    }
}
