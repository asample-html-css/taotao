package com.taotao.web.service;

import com.taotao.web.controller.httpclient.HttpResult;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
@Service
public class ApiService implements BeanFactoryAware {

//    /**
//     * @Service表示这个对象是单例,但是httpclient不能是单例
//     * 本质上是如何在单例对象中获取多例对象
//     * 解决思路就是,不在service中注入httpclient,而是每次从容器中获取
//     */

//httpclient由getHttpclient()方法注入
//    @Autowired
//    private CloseableHttpClient httpclient;

    @Autowired(required = false)
    private RequestConfig requestConfig;

    private BeanFactory beanFactory;

    /**
     * 指定GET请求，返回:null,请求失败，String数据，请求成功
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doGet(String url) throws ClientProtocolException, IOException {

        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = getHttpclient().execute(httpGet);
            // 判断返回状态是否为200
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 201) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }

        } finally {
            if (response != null) {
                response.close();
            }
        }
          return null;
    }

    /**
     * 指定带有参数的GET请求，返回:null,请求失败，String数据，请求成功
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doGet(String url, Map<String, String> params) throws ClientProtocolException, IOException ,URISyntaxException {

        URIBuilder builder = new URIBuilder(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.setParameter(entry.getKey(), entry.getValue());
        }
        return this.doGet(builder.build().toString());

    }


    /**
     * 带有参数的POST请求
     *
     * @param url
     * @param params
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, String> params) throws ParseException, IOException {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (null != params) {
            // 设置post参
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = getHttpclient().execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 没有参数的POST请求
     *
     * @param url
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public HttpResult doPost(String url) throws ParseException, IOException {
        return this.doPost(url, null);
    }


    /**
     * 获取HttpClient对象
     * @return
     */
    private CloseableHttpClient getHttpclient(){
        CloseableHttpClient   closeableHttpClient = this.beanFactory.getBean(CloseableHttpClient.class);

        return  closeableHttpClient;
    }

    /**
     * 初始化spring时候 由beanFactory注入多例的httpClient
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        //该方法实在spring容器初始化是会调用该方法,传入beanFactory
        //通过Bean工厂获取bean，保证HttpClient对象是多例
        this.beanFactory = beanFactory;

    }

}
