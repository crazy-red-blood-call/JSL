package com.antlord.dto.params;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserParam implements Serializable {

    private static final long serialVersionUID = -8265337645092532447L;

    /**
     * 账号名称
     */
    private String accountName;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别[M-男,F-女]
     */
    private String gender;

    /**
     * 是否锁定[1-锁定,0-正常]
     */
    private Integer lockFlag;

    private Integer pageNo;

    private Integer pageSize;
}
