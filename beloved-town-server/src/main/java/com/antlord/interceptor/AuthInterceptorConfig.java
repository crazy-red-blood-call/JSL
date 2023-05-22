package com.antlord.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @Description: 拦截器配置
 * @Author: CHENZHIWEI
 * @CreateTime: 2022-04-27 14:04:12
 */
@Configuration
public class AuthInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 添加拦截规则
        registry
                .addInterceptor(getInterceptorHandler())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/swagger-ui.html/**")
                .excludePathPatterns(Arrays.asList("/swagger**/**", "/doc.html**/**", "/swagger-resources/**", "/webjars/**", "/v3/**", "/error", "/favicon.ico"));
    }

    @Bean
    InterceptorHandler getInterceptorHandler() {
        return new InterceptorHandler();
    }

}
