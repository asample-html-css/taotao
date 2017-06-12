package com.taotao.manage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatData;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemCatService extends BaseService<ItemCat> {

    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private RedisService redisService;

    private static ObjectMapper MAPPER = new ObjectMapper();

    private static String REDIS_KEY = "TAOTAO_MANAGE_ITEM_CAT_ALL";

    private static Integer REDIS_TIME = 60 * 60 * 24 * 90;


    /**
     * 全部查询，并且生成树状结构
     *
     * @return
     */
    public ItemCatResult queryAllToTree() {
        try {
            //首先到缓存中命中
            String cacheData = redisService.get(REDIS_KEY);
            if (StringUtils.isNotEmpty(cacheData)) {//不为空，命中
                //将String转化成ItemCatResult
                return MAPPER.readValue(cacheData, ItemCatResult.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //如果没有命中  到数据库查询
        ItemCatResult result = new ItemCatResult();
        // 全部查出，并且在内存中生成树形结构
        List<ItemCat> cats = super.queryAll();

        // 转为map存储，key为父节点ID，value为数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
        for (ItemCat itemCat : cats) {
            if (!itemCatMap.containsKey(itemCat.getParentId())) {
                itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }


        // 封装一级对象
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for (ItemCat itemCat : itemCatList1) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
            result.getItemCats().add(itemCatData);
            if (!itemCat.getIsParent()) {
                continue;
            }

            // 封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
            itemCatData.setItems(itemCatData2);
            for (ItemCat itemCat2 : itemCatList2) {
                ItemCatData id2 = new ItemCatData();
                id2.setName(itemCat2.getName());
                id2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.add(id2);
                if (itemCat2.getIsParent()) {
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<String>();
                    id2.setItems(itemCatData3);
                    for (ItemCat itemCat3 : itemCatList3) {
                        itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                    }
                }
            }
            if (result.getItemCats().size() >= 14) {
                break;
            }
        }

        //在返回之前，将数据保存到缓存中 3个月
        try {
            redisService.set(REDIS_KEY, MAPPER.writeValueAsString(result), REDIS_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
