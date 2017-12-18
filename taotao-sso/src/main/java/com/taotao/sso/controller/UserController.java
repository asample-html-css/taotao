package com.taotao.sso.controller;

import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private static String COOKIE_NAME = "TT_LOGIN";

    public static final String COOKIE_NAME_ITEM_PAGE = "URL_TO_PAGE";


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
     * 登录 将登陆信息保存到redis, key保存到cookie中
     * @return
     */
    @RequestMapping(value = "doLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doLogin(@Param("username") String username, @Param("password") String password,
                                      HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            String token = userService.doLogin(username,password);
            if (token == null){
                //登录失败
                result.put("status",400);
            }else{

//                System.out.println(request.getRequestURI());
//                System.out.println(request.getRequestURL());

                //登录成功  将token保存到cookie中
                result.put("status",200);
                CookieUtils.setCookie(request,response,COOKIE_NAME,token);

                //从cookie中获取跳转链接的信息
//                String urlToPage = CookieUtils.getCookieValue(request,COOKIE_NAME_ITEM_PAGE);
//                if(StringUtils.isNoneEmpty(urlToPage)){
//                    response.sendRedirect(urlToPage);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //登录失败
            result.put("status",500);
        }

        return result;
    }


    /**
     * 根据token查询用户信息  token是前端从cookie中获取,传到后端的
     * @return
     */
    @RequestMapping(value = "{token}",method = RequestMethod.GET)
    public ResponseEntity<User> queryByToken(@PathVariable("token")String token){

        try {
            User user = this.userService.queryByToken(token);
            if (user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }








}
