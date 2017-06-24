package com.taotao.manage.controller.api;

import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by dd876799869 on 2017/6/6.
 */
@Controller
@RequestMapping("api/item/cat")
public class ApiItemCatController {

    @Autowired
    private ItemCatService itemCatService;


    /**
     * 对外提供查询商品类目接口
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemCat(){
        try {
            ItemCatResult json = itemCatService.queryAllToTree();
            return  ResponseEntity.ok(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
