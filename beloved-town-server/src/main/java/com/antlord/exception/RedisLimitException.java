package com.antlord.exception;

import com.antlord.enums.RsultCodeEnum;
import lombok.Getter;

/**
 * @Description: redis限流异常处理
 * @Author: CHENZHIWEI
 * @CreateTime: 2022-11-14 15:51:59
 */
@Getter
public class RedisLimitException extends RuntimeException {
    private RsultCodeEnum resultCodeEnum;

    public RedisLimitException(RsultCodeEnum resultCode) {
        this.resultCodeEnum = resultCode;
    }
}
