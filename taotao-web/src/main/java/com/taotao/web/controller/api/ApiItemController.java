package com.taotao.web.controller.api;

import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yangdongan on 2017/6/6 0006.
 */
@Controller
@RequestMapping("api/item/cat")
public class ApiItemController {

    @Autowired
    private ItemCatService itemCatServicep;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemCat(){







    }
}
