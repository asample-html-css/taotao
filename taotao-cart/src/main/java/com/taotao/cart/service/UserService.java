package com.taotao.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.pojo.User;
import com.taotao.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * Created by Administrator on 2017/7/13 0
 @Service013.
 */
@Service
public class UserService {

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Value("${TAOTAO_SSO_URL}")
    public  String TAOTAO_SSO_URL;

    /**
     * 根据token查询
     * @param token
     * @return
     */
    public User queryByToken(String token) {
        String key = "TOKEN_" + token;
        String jsonData = redisService.get(key);
        if (jsonData == null) {
            return null;
        }
        //非常重要  重新刷新缓存时间
        redisService.expire(key, 60 * 30);
                try {
                return mapper.readValue(jsonData, User.class);
        } catch (Exception e) {
        e.printStackTrace();
        }
        return null;
        }
        }
