package com.taotao.manage.controller;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.common.service.ApiService;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by dd876799869 on 2017/5/29.
 */
@Controller
@RequestMapping("content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ApiService apiService;

    @Value("${TAOTAO_WEB_URL}")
    private String TAOTAO_WEB_URL;


    /**
     * 新增内容
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Content> saveContent(Content content) {
        try {
            content.setId(null);
            this.contentService.saveContent(content);
            return ResponseEntity.status(HttpStatus.CREATED).body(content);//400
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 删除内容
     * @return
     */
    @RequestMapping(value = ("delete"),method = RequestMethod.POST)
    public ResponseEntity<Void> deleteContent(long[] ids) {
        try {
            int i =   this.contentService.deleteContent(ids);
            if(i == ids.length){
//                return ResponseEntity.status(200).build();
                return ResponseEntity.ok(null);//200
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//400
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }




    /**
     * 查询内容列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryContent(@RequestParam(value = "categoryId")Long categoryId,
                                                     @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                     @RequestParam(value = "rows",defaultValue = "10") Integer rows) {
        try {
            EasyUIResult easyUIResult =  this.contentService.queryContentListByCategoryId(categoryId,page,rows);
            return ResponseEntity.status(HttpStatus.CREATED).body(easyUIResult);//400
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
