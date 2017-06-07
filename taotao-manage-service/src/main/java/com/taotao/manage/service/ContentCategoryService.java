package com.taotao.manage.service;

import com.taotao.manage.pojo.ContentCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/7 0007.
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

    /**
     * 删除节点
     * @param contentCategory
     */
    public void deleteAll(ContentCategory contentCategory) {
        //创建容器，所有需要删除的节点的id封装进来
        List<Object> ids = new ArrayList<Object>();
        ids.add(contentCategory.getId());
        //递归查询该节点所辖所有节点
        this.findAllSubNodeIds(ids,contentCategory.getId());
        super.deleteByIds(ids,ContentCategory.class,"id");

        //判断有没有兄弟节点  若没有则将父节点改为子节点
        ContentCategory record = new ContentCategory();
        record.setParentId(contentCategory.getParentId());
        List<ContentCategory> list = super.queryListByWhere(record);
        if (null == list ||list.isEmpty() ){
            ContentCategory parentRecord = new ContentCategory();
            parentRecord.setId(contentCategory.getParentId());
            parentRecord.setIsParent(false);
            super.updateSelective(parentRecord);
        }
    }

    /**
     *  递归查询该节点所辖所有节点
     * @param ids
     * @param pid
     */
    private void findAllSubNodeIds(List<Object> ids, Long pid) {
        ContentCategory record = new ContentCategory();
        record.setParentId(pid);
        List<ContentCategory> list = super.queryListByWhere(record);
        for (ContentCategory contentCategory:list) {
            //首先将自己放进去
            ids.add(contentCategory.getId());
            //如果自己是父节点的话  开始递归 将id当成parentId
            if (contentCategory.getIsParent()){
                findAllSubNodeIds(ids, contentCategory.getId());
            }
        }
    }
}
