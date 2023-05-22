package com.antlord.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = -768135222212134352L;

    /**
     * 账号名称
     */
    private String accountName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 角色列表
     */
    private List<String> roleNameList;
}
