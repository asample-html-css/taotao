package com.taotao.web.interceptors;

import com.taotao.common.utils.CookieUtils;
import com.taotao.web.bean.User;
import com.taotao.web.service.UserService;
import com.taotao.web.thread.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 登录拦截器
 * Created by dd876799869 on 2017/7/15.
 */
public class UserLoginInterceptors implements HandlerInterceptor {


    public static final String COOKIE_NAME = "TT_LOGIN";
    public static final String COOKIE_NAME_ITEM_PAGE = "URL_TO_PAGE";


    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
       String loginUrl = userService.TAOTAO_SSO_URL + "/user/login";
//       String toItemPageUrl = httpServletRequest.getRequestURL().toString();
       UserThreadLocal.set(null);//得益于tomcat线程池的机制  本地线程类是可复用的 必须清空

//        String loginUrl = "http://sso.taotao.com/service/user/login";

        //1,判断cookie中是否存在token
        String token = CookieUtils.getCookieValue(httpServletRequest,COOKIE_NAME);
        if (token == null){
            //1,将redirect保存到cookie中
//            CookieUtils.setCookie(httpServletRequest,httpServletResponse,COOKIE_NAME_ITEM_PAGE,toItemPageUrl);
            //2，跳转到登录页面
            httpServletResponse.sendRedirect(loginUrl);
            return false;
        }
        //2,判断redis中的token是否已经超时
        User user = userService.queryUserByToken(token);
        if (user == null){
            //1,将redirect保存到cookie中
//            CookieUtils.setCookie(httpServletRequest,httpServletResponse,COOKIE_NAME_ITEM_PAGE,toItemPageUrl);
            //2，跳转到登录页面
            httpServletResponse.sendRedirect(loginUrl);
            return false;
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
