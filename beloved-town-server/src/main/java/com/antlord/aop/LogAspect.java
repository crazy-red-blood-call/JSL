/*
 * Copyright 2021-2021 Wenge Group Holding Ltd.
 */

package com.antlord.aop;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.antlord.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.io.InputStreamSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 全局自动日志打印
 *
 * @author chenzhiwei
 * @since 2022-09-07
 */
@Component
@Aspect
@Slf4j
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class LogAspect {

    @Autowired
    private HttpServletRequest autowiredRequest;

    @Pointcut("execution( * com.antlord..controller..*.*(..))")
    public void logPointCut() {
    }

    /**
     * 操作日志aop
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        // 获取当前method对象
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        Scheduled scheduled = method.getAnnotation(Scheduled.class);
        if (null != scheduled) {
            return null;
        }

        // 如果有全局日志注解，则获取开关
        String logJsonStr = getParam(args, sign);
        SysUser userInfo = null;
        try {
//            userInfo = JwtConfig.getUserInfo();
            userInfo = new SysUser();
        } catch (Exception e) {
            userInfo = new SysUser();
            log.error("{} ===> {}", autowiredRequest.getRequestURI(), e.getMessage());
        }
        String user = "";
        if (StrUtil.isNotBlank(userInfo.getAccountName())) {
            user = "[user]【" + userInfo.getAccountName() + "】  ";
        }
        log.info("{}〓〓〓〓〓〓〓〓〓〓    " +
                        "■ [uri] : {}   " +
                        "■ [param] : {}",
                user, autowiredRequest.getRequestURI(), logJsonStr);


        Object ret = null;
        // 接口执行前拦截

        String retStr = "result";
        try {
            ret = joinPoint.proceed();
            retStr = JSON.toJSONString(ret);
        } catch (Exception e) {
            log.error("■ ■ ■ ■ ■ ■ ■ ■ ■ ■    " +
                            "■ [uri] : {}   " +
                            "■ [result] : {}",
                    autowiredRequest.getRequestURI(), e.getMessage());
            throw e;
        }
        log.info("■ ■ ■ ■ ■ ■ ■ ■ ■ ■    " +
                        "■ [uri] : {}   " +
                        "■ [result] : {}" ,
                autowiredRequest.getRequestURI(), retStr);

        return ret;
    }

    public static String getParam(Object[] args, MethodSignature sign) {
        String logJsonStr = "param";
        String[] parameterNames = sign.getParameterNames();
        JSONObject logJson = new JSONObject();
        int length = args.length;

        for (int i = 0; i < length; i++) {
            // 不打印文件,ServletResponse,ServletRequest日志
            if (args[i] instanceof InputStreamSource
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof ServletRequest) {
                logJson.put(parameterNames[i], parameterNames[i]);
                continue;
            }
            logJson.put(parameterNames[i], args[i]);
        }
        try {
            logJsonStr = JSON.toJSONString(logJson);
        } catch (Exception e) {
            e.getMessage();
        }
        return logJsonStr;
    }

}
