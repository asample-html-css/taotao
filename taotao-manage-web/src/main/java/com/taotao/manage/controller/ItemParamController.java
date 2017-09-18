package com.taotao.manage.controller;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by dd876799869 on 2017/6/4.
 */
@Controller
@RequestMapping("item/param")
public class ItemParamController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);


    @Autowired
    private ItemParamService itemParamService;

    /**
     * 查询规格参数模板是否存在
     *
     * @return
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryItemParam(@PathVariable("itemCatId") Long itemCatId) {
        try {
            ItemParam record = new ItemParam();
            record.setItemCatId(itemCatId);
            ItemParam itemParam = itemParamService.queryOne(record);
            if (itemParam == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);//没有找到404
            }
            return ResponseEntity.ok(itemParam);//200
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//500
    }

    /**
     * 新增商品规格参数模板
     *
     * @return
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId") Long itemCatId,
                                              @RequestParam("paramData") String paramData) {

        try {
            ItemParam record = new ItemParam();
            record.setId(null);
            record.setItemCatId(itemCatId);
            record.setParamData(paramData);
            itemParamService.save(record);
            return ResponseEntity.status(HttpStatus.CREATED).build();//400
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
    }

    /**
     * 查询商品规格参数列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemParamList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        try {
            return ResponseEntity.ok(this.itemParamService.queryItemParamList(page, rows));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("查询商品规格参数列表! page ={}" + page + "， rows = " + rows, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//500
    }
}
