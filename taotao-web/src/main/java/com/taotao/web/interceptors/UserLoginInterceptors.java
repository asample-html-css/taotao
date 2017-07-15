package com.taotao.web.interceptors;

import com.taotao.common.utils.CookieUtils;
import com.taotao.web.bean.User;
import com.taotao.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
       String loginUrl = userService.TAOTAO_SSO_URL + "/user/login";

//        String loginUrl = "http://sso.taotao.com/service/user/login";

        //1,判断cookie中是否存在token
        String token = CookieUtils.getCookieValue(httpServletRequest,COOKIE_NAME);
        if (token == null){
            httpServletResponse.sendRedirect(loginUrl);
            return false;
        }
        //2,判断redis中的token是否已经超时
        User user = userService.queryUserByToken(token);
        if (user == null){
            httpServletResponse.sendRedirect(loginUrl);
            return false;
        }
        //走到这里 登录成功 返回确认订单页  放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
