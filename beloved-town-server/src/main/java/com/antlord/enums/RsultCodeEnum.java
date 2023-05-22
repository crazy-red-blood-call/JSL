package com.antlord.enums;

import lombok.Getter;

@Getter
public enum RsultCodeEnum {

    // 系统通用--------------------------------------------------------------------------------
    SUCCESS("000000", "成功"),
    ACCOUNT_NAME_BLANK("000001", "用户名不能为空"),
    PASSWORD_BLANK("000002", "密码不能为空"),
    ACCOUNT_NAME_NOT_EXIT("000003", "账号不存在"),
    PASSWORD_ERROR("000004", "密码错误"),
    AUTHORIZATION_BLANK("000005", "无token"),
    AUTHORIZATION_SIGN("000006", "无效签名"),
    AUTHORIZATION_EXPIRED("000007", "登录过期"),
    AUTHORIZATION_ALGORITHM("000008", "token算法不一致"),
    AUTHORIZATION_ERROR("000009", "无效签名"),
    CIPHER_IS_BLANK("000010", "接口签名不能为空"),
    CIPHER_IS_FAILED("000011", "接口签名错误"),
    SYSTEM_SO_BUSY_ERROR("000012", "系统繁忙"),
    ACCOUNT_LOCK("000013", "账号已锁定"),
    ADMIN_NOT_DELETE("000014", "超级管理员不可删除"),
    ADMIN_NOT_EDIT("000015", "超级管理员不可编辑"),
    ERROR("999998", "系统异常"),
    FAILED("999999", "失败"),

    // sysUser 用户------------------------------------------------------------------------------
    USER_EXIST("010001", "用户已存在"),



    ;
    private String code;
    private String msg;

    RsultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
