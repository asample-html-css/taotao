package com.taotao.manage.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.common.service.ApiService;
import com.taotao.common.service.RedisService;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7 0007.
 */
@Service
public class ContentService extends BaseService<Content> {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisService redisService;


    @Autowired
    private ApiService apiService;

    @Value("${TAOTAO_WEB_URL}")
    private String TAOTAO_WEB_URL;

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

    /**
     * 插入 更新 内容
     * @param content
     * @return
     */
    public void saveContent(Content content) {
        clearCache();
        //插入 更新数据
        super.saveSelective(content);
    }

    /**
     * 根據ID刪除數據
     * @param ids
     * @return
     */
    public  int deleteContent(long[] ids){
        clearCache();
        //删除数据库
        return contentMapper.deleteContent(ids);
    }

    /**
     * 插入 更新 删除 内容的时候通知其他系统清空缓存
     */
    private void clearCache() {
        String url = TAOTAO_WEB_URL + "/content/cache.html";
        try {
            this.apiService.doPost(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
