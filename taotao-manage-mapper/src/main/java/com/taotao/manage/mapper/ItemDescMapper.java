package com.taotao.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.pojo.ItemDesc;
import org.apache.ibatis.annotations.Param;

/**
 * Created by dd876799869 on 2017/4/27.
 */

public interface ItemDescMapper extends Mapper<ItemDesc> {

    /**
     * 根据id删除数据
     * @param ids
     * @return
     */
    int deleteItemDesc(@Param("ids") long[] ids);


}
