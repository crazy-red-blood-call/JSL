package com.antlord.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = -8265337645092532447L;

    /**
     * 用户id
     */
    private String userId;

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
     * 登录密码
     */
    private String password;

    /**
     * 性别[M-男,F-女]
     */
    private String gender;

    /**
     * 是否锁定[1-锁定,0-正常]
     */
    private Integer lockFlag;
    private String createTime;
    private String updateTime;

    /**
     * 角色列表
     */
    private List<String> roleNameList;

    /**
     * 角色id
     */
    private String roleIds;
    private List<Integer> roleIdList;

}
