package com.taotao.common.service;

/**
 * Created by yangdongan on 2017/6/12 0012.
 */
public interface Function<T,E> {

    public T callback(E e);
}
