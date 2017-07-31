package com.taotao.cart.interceptors;

import com.taotao.cart.pojo.User;
import com.taotao.cart.service.UserService;
import com.taotao.cart.thread.UserThreadLocal;
import com.taotao.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * Created by dd876799869 on 2017/7/15.
 */
public class UserLoginInterceptors implements HandlerInterceptor {

    public static final String COOKIE_NAME = "TT_LOGIN";

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        UserThreadLocal.set(null);//得益于tomcat线程池的机制  本地线程类是可复用的 必须清空

        //1,判断cookie中是否存在token
        String token = CookieUtils.getCookieValue(httpServletRequest,COOKIE_NAME);
        if (token == null){
           //没有登录也放行
            return true;
        }
        //2,判断redis中的token是否已经超时
        User user = userService.queryByToken(token);
        if (user == null){
            //登录超时
            return true;
        }
        //走到这里 登录成功 返回确认订单页  放行
        UserThreadLocal.set(user); //将拦截器查寻的User保存到本地线程中 传递到controller
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
