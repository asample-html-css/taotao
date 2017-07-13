package com.taotao.sso.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/13 0013.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;

    private static final ObjectMapper mapper = new ObjectMapper();


    /**
     * 检测参数是否可用
     *
     * @param param
     * @param type  1 username 2 phone 3 email
     * @return
     */
    public Boolean check(String param, Integer type) {
        User record = new User();
        switch (type) {
            case 1:
                record.setUsername(param);
                break;
            case 2:
                record.setPhone(param);
                break;
            case 3:
                record.setEmail(param);
                break;
            default:
                return null;
        }
        //true 该参数没有被注册,可以使用
        return userMapper.selectOne(record) == null;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    public Boolean doRegister(User user) {

        user.setId(null);
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return userMapper.insert(user) == 1;
    }

    /**
     * 登录
     *
     * @param username
     * @return
     */
    public String doLogin(String username, String password) throws Exception {
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        //校验用户名
        if (user == null) {//登录失败
            return null;
        }
        //校验密码
        if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
            return null;
        }
        //至此,登录成功
        //生成token  保存到redis中
        String token = DigestUtils.md5Hex(user.getPassword() + username + System.currentTimeMillis());
        this.redisService.set("TOKEN_" + token, mapper.writeValueAsString(user), 60 * 30);
        return token;
    }
}
