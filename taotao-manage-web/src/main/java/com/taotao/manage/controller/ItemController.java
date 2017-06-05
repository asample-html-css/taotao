package com.taotao.manage.controller;

import com.mysql.jdbc.StringUtils;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("item")
public class ItemController {

   private static final Logger LOGGER  = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;


    /**
     * 新增商品
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc")String desc){
        try {
            if (LOGGER.isDebugEnabled()){
                //入参处记录日志
                LOGGER.debug("新增商品：item= {}, desc={}",item,desc);
            }
            //简单模拟400请求参数错误
            if (StringUtils.isNullOrEmpty(item.getTitle())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//400
            }
            //保存商品
           Boolean boo =  itemService.saveItem(item,desc);
            if (!boo){
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("新增商品失败：item= {}, desc={}", item, desc);
                }
                //保存商品失败
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();//400
        } catch (Exception e) {
            LOGGER.error("新增商品出错! item = " + item, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
    }

    /**
     * 查询商品列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value ="page",defaultValue ="1")Integer page,
                                                      @RequestParam(value ="rows",defaultValue ="30")Integer rows){
        try {
            return ResponseEntity.ok(this.itemService.queryItemList(page,rows));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("查询商品出错! page ={}"+page+"， rows = "+rows, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//500
    }

    /**
     * 根据cid查找cname
     * @param cid
     * @return
     */
    @RequestMapping(value = "/queryCname",method = RequestMethod.POST)
    public ResponseEntity<String> queryCname(@RequestParam("cid")Long cid){
        try {
            return ResponseEntity.ok(this.itemService.queryCname(cid));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("根据cid查找cname出错! cid ={}"+cid, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//500
    }

    /**
     * 更新商品
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc")String desc){
        try {
            if (LOGGER.isDebugEnabled()){
                //入参处记录日志
                LOGGER.debug("更新商品：item= {}, desc={}",item,desc);
            }
            //简单模拟400请求参数错误
            if (StringUtils.isNullOrEmpty(item.getTitle())){
                if (LOGGER.isInfoEnabled()){
                    LOGGER.info("编辑商品参数不合法，item = {},desc = {}",item,desc);
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//400
            }
            //更新商品 204
            Boolean boo =  itemService.updateItem(item,desc);
            if (!boo){//500
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("更新商品失败：item= {}, desc={}", item, desc);
                }
                //保存商品失败
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();//204
        } catch (Exception e) {
            LOGGER.error("更新商品出错! item = " + item, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
    }



}
