package com.antlord.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JwtUserBean implements Serializable {

    private static final long serialVersionUID = -4429920974429680040L;

    private String accountName;
    private String phone;
    private String userId;
    private String token;
    private List<String> roleNameList;

    /**
     * 用户姓名
     */
    private String userName;

}
