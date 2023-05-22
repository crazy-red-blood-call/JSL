package com.antlord.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @作者 yangs
 * @日期 2021/9/25
 * @描述 配置映射关系，可通过网络路径访问本地文件
 */
@Configuration
public class WebConf extends WebMvcConfigurationSupport {

    @Value("${file.local}")
    private String filePath;
    @Value("${file.run}")
    private String runPath;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //映射static路径的请求到static目录下
        // 静态资源访问路径和存放路径配置
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        // swagger访问配置
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/");
        //通过image访问本地的图片
        registry.addResourceHandler(runPath).addResourceLocations(filePath);
    }

}