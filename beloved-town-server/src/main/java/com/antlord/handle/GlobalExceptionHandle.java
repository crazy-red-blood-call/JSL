package com.antlord.handle;

import com.antlord.bean.Result;
import com.antlord.enums.RsultCodeEnum;
import com.antlord.exception.AuthorizationException;
import com.antlord.exception.RedisLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e, HttpServletResponse resp) {
        log.error("=========exception============{}", e.getMessage(), e);
        return Result.error();
    }


    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Result authorizationException(AuthorizationException e, HttpServletResponse resp) {
        log.error("=========authorizationException============{}", e.getMessage());
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return Result.error(RsultCodeEnum.AUTHORIZATION_EXPIRED);
    }


    @ExceptionHandler(RedisLimitException.class)
    @ResponseBody
    public Result redisLimitException(RedisLimitException e, HttpServletResponse resp) {
        log.error("=========redisLimitException============{}", e.getMessage());
        return Result.error(RsultCodeEnum.SYSTEM_SO_BUSY_ERROR);
    }
}
