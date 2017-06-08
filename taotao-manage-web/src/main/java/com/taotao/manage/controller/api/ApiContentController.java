package com.taotao.manage.controller.api;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/content")
public class ApiContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 查询内容列表(对外提供查询数据的接口)
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
