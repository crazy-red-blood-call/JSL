package com.antlord.interceptor;


import cn.hutool.core.util.StrUtil;
import com.antlord.config.JwtConfig;
import com.antlord.enums.RsultCodeEnum;
import com.antlord.utils.ResponseUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Description: 拦截请求
 * @Author: CHENZHIWEI
 * @CreateTime: 2022-04-27 14:04:33
 */
@Slf4j
@Data
@Component
public class AuthInterceptor implements InterceptorBase {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(true){
            return true;
        }
        // 获取token
        String authorization = request.getHeader("Authorization");

        // 无token，则拦截请求
        if (StrUtil.isBlank(authorization)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ResponseUtil.resultMsg(response, RsultCodeEnum.AUTHORIZATION_BLANK);
            return false;
        }
        try {
            // 解析token，解析失败则拦截请求
            DecodedJWT decodedJWT = JwtConfig.verifyToken(authorization);
            if (null == decodedJWT) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                ResponseUtil.resultMsg(response, RsultCodeEnum.AUTHORIZATION_EXPIRED);
                return false;
            }
        } catch (Exception e) {
            log.error("===>{}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ResponseUtil.resultMsg(response, RsultCodeEnum.AUTHORIZATION_EXPIRED);
            return false;
        }

        return true;
    }

    @Override
    public int getSort() {
        return 0;
    }

}

