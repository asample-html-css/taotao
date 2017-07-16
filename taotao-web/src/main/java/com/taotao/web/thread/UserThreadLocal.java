package com.taotao.web.thread;

import com.taotao.web.bean.User;

/**
 * 本地线程类 解决User信息查寻多次的问题
 * Created by dd876799869 on 2017/7/16.
 */
public class UserThreadLocal {

    public static final ThreadLocal<User> THREAD_LOCAL = new ThreadLocal<User>();

    //私有构造
    private UserThreadLocal() {
    }


    public static User get() {
        return THREAD_LOCAL.get();
    }

    public static void set(User user) {
        THREAD_LOCAL.set(user);
    }


}
