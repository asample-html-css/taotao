package com.taotao.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.web.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by dd876799869 on 2017/7/15.
 */
@Service
public class UserService {
    @Value("${TAOTAO_SSO_URL}")
    public  String TAOTAO_SSO_URL;

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 根据token查询User
     * @param token
     * @return
     */
    public com.taotao.web.bean.User queryUserByToken(String token) {

        try {
            String jsonData = apiService.doGet(TAOTAO_SSO_URL + "/user/" + token);
            if (jsonData != null) {
               return mapper.readValue(jsonData, User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
