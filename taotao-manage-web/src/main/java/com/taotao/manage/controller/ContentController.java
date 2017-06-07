package com.taotao.manage.controller;

import com.taotao.manage.pojo.Content;
import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;
import com.taotao.manage.service.ContentService;
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
@RequestMapping("content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 新增内容
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Content> saveContentCategory(Content content) {
        try {
            content.setId(null);
            this.contentService.saveSelective(content);
            return ResponseEntity.status(HttpStatus.CREATED).body(content);//400
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}
