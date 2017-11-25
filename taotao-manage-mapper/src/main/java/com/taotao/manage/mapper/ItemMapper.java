package com.taotao.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Item;
import org.apache.ibatis.annotations.Param;

/**
 * Created by dd876799869 on 2017/4/27.
 */

public interface ItemMapper extends Mapper<Item> {


    /**
     * 删除商品信息
     * @param ids
     * @return
     */
    int deleteItem(@Param("ids")long[] ids);
}
