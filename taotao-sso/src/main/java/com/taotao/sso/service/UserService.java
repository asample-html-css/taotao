package com.taotao.sso.service;

import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
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
        return userMapper.selectOne(record)==null;
    }

    /**
     * 注册
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
}
