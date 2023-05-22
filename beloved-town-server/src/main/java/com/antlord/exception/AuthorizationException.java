package com.antlord.exception;

import com.antlord.enums.RsultCodeEnum;
import lombok.Getter;


@Getter
public class AuthorizationException extends RuntimeException{

    private static final long serialVersionUID = -6000841390904892175L;

    private RsultCodeEnum rsultCodeEnum;

    public AuthorizationException(RsultCodeEnum rsultCodeEnum) {
        super(rsultCodeEnum.getMsg());
        this.rsultCodeEnum = rsultCodeEnum;
    }
}
