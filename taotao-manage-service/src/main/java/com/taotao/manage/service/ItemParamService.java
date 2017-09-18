package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.mapper.ItemParamMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dd876799869 on 2017/5/29.
 */
@Service
public class ItemParamService extends BaseService<ItemParam> {

    @Autowired
    private ItemParamMapper itemParamMapper;

//    @Autowired
//    private ItemCatMapper itemCatMapper;
    /**
     * 查询商品规格参数列表
     * @return
     */
    public EasyUIResult queryItemParamList(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        Example example = new Example(ItemParam.class);
        example.setOrderByClause("created desc");
        List<ItemParam> itemParamlist = this.itemParamMapper.queryItemParamList(page,rows);
        //遍历
//        for (int i =0;i<itemParamlist.size();i++){
//            //根据类目id获取类目名称
//            ItemCat itemCat = new ItemCat();
//            itemCat.setId(itemParamlist.get(i).getItemCatId());
//            String itemCatName = itemCatMapper.selectOne(itemCat).getName();
//            itemParamlist.get(i).setItemCatName(itemCatName);
//        }
        PageInfo<ItemParam> pageInfo = new PageInfo<ItemParam>(itemParamlist);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }
}
