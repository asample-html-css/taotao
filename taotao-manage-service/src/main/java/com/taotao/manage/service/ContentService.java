package com.taotao.manage.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7 0007.
 */
@Service
public class ContentService extends BaseService<Content> {

    @Autowired
    private ContentMapper contentMapper;

    /**
     * 查询内容列表
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    public EasyUIResult queryContentListByCategoryId(Long categoryId, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<Content> list = contentMapper.queryContentListByCategoryId(categoryId);
        PageInfo<Content> pageInfo = new PageInfo<Content>(list);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }
}
