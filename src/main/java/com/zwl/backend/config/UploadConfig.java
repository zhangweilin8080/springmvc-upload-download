package com.zwl.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.List;

/**
 * @author zwl
 * @date 2021/1/6 14:19
 * @describe instructions...
 */

@Configuration
public class UploadConfig {
    //显示声明CommonsMultipartResolver为mutipartResolver
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        //设置请求的编码格式, 默认为iso-8859-1
        resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        //内存中的最大值
        resolver.setMaxInMemorySize(40960);
        //设置允许上传文件的最大值, 单位为字节 1k=1024字节
        resolver.setMaxUploadSize(100*1024);
        return resolver;
    }


}
