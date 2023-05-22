package com.antlord.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface InterceptorBase {

    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler);

    /**
     * 拦截器排序
     * @return
     */
    int getSort();
}
