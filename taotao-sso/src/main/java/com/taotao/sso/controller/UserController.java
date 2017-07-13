package com.taotao.sso.controller;

import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dd876799869 on 2017/7/12.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册页面
     */
    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String toRegister(){
        return "register";
    }

    /**
     * 登录页面
     */
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String toLogin(){
        return "login";
    }


    /**
     * 检测是否可用(注册功能的时候)
     * @return
     */
    @RequestMapping(value = "{param}/{type}",method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param")String param, @PathVariable("type")Integer type){

        try {
            Boolean bool = this.userService.check(param,type);
            if (bool == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(!bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    /**
     * 注册
     * @return
     */
    @RequestMapping(value = "doRegister",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doRegister(@Valid User user, BindingResult bindingResult){
        Map<String,Object> result = new HashMap<String,Object>();
        if (bindingResult.hasErrors()){
            result.put("status",400);
            List<String> msgs = new ArrayList<String>();
            List<ObjectError> allErrors = bindingResult.getAllErrors();

            for (ObjectError objectError : allErrors) {
                msgs.add(objectError.getDefaultMessage());
            }

            result.put("data","参数错误! " + StringUtils.join(msgs,'|'));
            return result;
        }

        try {
            Boolean bool = userService.doRegister(user);
            if (bool){//注册成功
                result.put("status",200);
            }else{
                result.put("status",500);
                result.put("data","哈哈哈");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status",500);
            result.put("data","哈哈哈");
        }
        return result;
    }



    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "doLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doLogin(User user){

        return null;
    }





}
