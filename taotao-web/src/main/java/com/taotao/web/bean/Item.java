package com.taotao.web.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by dd876799869 on 2017/6/24.
 */
public class Item extends com.taotao.manage.pojo.Item {

    //继承pojo.Item  增加一个getImages方法
    public String[] getImages() {
        return StringUtils.split(super.getImage(),',');
    }
}
