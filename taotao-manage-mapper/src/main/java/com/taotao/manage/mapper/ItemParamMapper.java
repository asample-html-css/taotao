package com.taotao.manage.mapper;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.ItemParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by dd876799869 on 2017/4/27.
 */

public interface ItemParamMapper extends Mapper<ItemParam> {

    /**
     * 查询规格参数列表
     * @param example
     * @return
     */
    List<ItemParam> queryItemParamList(@Param("page") Integer page, @Param("rows")Integer rows);
}
