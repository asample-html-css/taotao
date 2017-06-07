package com.taotao.manage.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by dd876799869 on 2017/6/6.
 */
@Controller
@RequestMapping("api/item/cat")
public class ApiItemController {

    @Autowired
    private ItemCatService itemCatService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 对外提供查询商品类目接口
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> queryItemCat(
            @RequestParam(value = "callback",required = false)String callback){

        try {
            ItemCatResult itemCatResult = itemCatService.queryAllToTree();

            String json = MAPPER.writeValueAsString(itemCatResult);//将集合对象转化为json字符串

            if(StringUtils.isEmpty(callback)){//不需要跨域请求
                return  ResponseEntity.ok(json);

            }else{//需要跨域请求
                return  ResponseEntity.ok(callback +"("+json+")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
