package com.taotao.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Content;

import java.util.List;

/**
 * Created by yangdongan on 2017/6/7 0007.
 */
public interface ContentMapper extends Mapper<Content> {


    /**
     * 查询内容列表
     */
    List<Content> queryContentListByCategoryId(Long categoryId);



}
