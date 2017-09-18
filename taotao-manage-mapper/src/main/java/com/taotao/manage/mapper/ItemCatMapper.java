package com.taotao.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.ItemCat;
import org.apache.ibatis.annotations.Param;

/**
 * Created by dd876799869 on 2017/4/27.
 */

public interface ItemCatMapper extends Mapper<ItemCat> {

    /**
     * 根据cid查找canme
     * @param cid
     * @return
     */
    String queryCname(@Param("cid") Long cid);
}
