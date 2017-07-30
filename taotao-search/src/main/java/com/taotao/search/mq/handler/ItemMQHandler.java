package com.taotao.search.mq.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.search.bean.Item;
import com.taotao.search.service.ItemMQService;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dd876799869 on 2017/7/30.
 */
public class ItemMQHandler {


    @Autowired
    private ItemMQService itemMQService;

    @Autowired
    private HttpSolrServer httpSolrServer;


    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void execute(String msg) {
        try {
            JsonNode jsonNode = MAPPER.readTree(msg);
            Long itemId = jsonNode.get("itemId").asLong();
            String type = jsonNode.get("type").asText();
            if ("update".equals(type) || "insert".equals(type)){//更新solr中的数据
                Item item = this.itemMQService.queryItemById(itemId);
                this.httpSolrServer.addBean(item);
                this.httpSolrServer.commit();
            }else if ("delete".equals(type)){//删除solr中的数据
                this.httpSolrServer.deleteById(itemId.toString());
                this.httpSolrServer.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
