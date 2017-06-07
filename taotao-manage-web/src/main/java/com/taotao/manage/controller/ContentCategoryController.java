package com.taotao.manage.controller;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * Created by dd876799869 on 2017/5/29.
 */
@Controller
@RequestMapping("content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 查询内容分类列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryContentCategoryListByParentId(
            @RequestParam(value = "id", defaultValue = "0") Long pid) {
        try {
            ContentCategory record = new ContentCategory();
            record.setParentId(pid);
            List<ContentCategory> list = this.contentCategoryService.queryListByWhere(record);
            if (null == list || list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    /**
     * 新增子节点
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory) {
        try {
            //保存子节点
            contentCategory.setId(null);
            contentCategory.setIsParent(false);
            contentCategory.setStatus(1);
            contentCategory.setSortOrder(1);
            this.contentCategoryService.saveSelective(contentCategory);

            //判断现在的父节点的isParent是否是true  如果不是则改为true
            ContentCategory parent = contentCategoryService.queryById(contentCategory.getParentId());
            if (!parent.getIsParent()){
                parent.setIsParent(true);
                this.contentCategoryService.updateSelective(parent);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);//400
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 重命名节点
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> rename(@RequestParam("id") Long id,@RequestParam("name") String name) {
        try {
            ContentCategory record = new ContentCategory();
            record.setId(id);
            record.setName(name);
            this.contentCategoryService.updateSelective(record);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }



    /**
     * 删除节点 1，删除自己  2，删除所辖子节点   3，更改父节点的状态
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteNode(ContentCategory contentCategory) {
        try {
            this.contentCategoryService.deleteAll(contentCategory);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }




}
