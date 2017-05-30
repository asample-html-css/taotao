package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by dd876799869 on 2017/5/30.
 */
@Service
public class PropertiesService {
    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;
}
