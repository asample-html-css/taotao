package com.taotao.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by dd876799869 on 2017/4/26.
 */

@Controller
@RequestMapping("page")
public class PageController {
    /**
     * 通用页面跳转
     * @param pageName
     * @return
     */

    @RequestMapping(value = "{pageName}", method = RequestMethod.GET)
    public String toPage(@PathVariable(value = "pageName") String pageName) {
        return pageName;
    }


}
