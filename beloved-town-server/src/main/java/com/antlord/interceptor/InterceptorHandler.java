package com.antlord.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InterceptorHandler implements HandlerInterceptor {

    /**
     * 注入拦截器
     */
    @Autowired
    private Map<String, InterceptorBase> handleMap;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 按顺序依次执行拦截器
        if (null != handleMap && handleMap.size() != 0) {
            // 按sort排序
            List<InterceptorBase> preHandleList = handleMap.values().stream().sorted(Comparator.comparingInt(InterceptorBase::getSort)).collect(Collectors.toList());
            for (InterceptorBase preHandle : preHandleList) {
                boolean isPass = preHandle.preHandle(request, response, handler);
                if (!isPass) {
                    return false;
                }
            }
        }
        return true;
    }
}
