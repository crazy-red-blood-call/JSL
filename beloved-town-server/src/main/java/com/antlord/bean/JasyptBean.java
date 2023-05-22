package com.antlord.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class JasyptBean implements Serializable {

    private static final long serialVersionUID = 7085818304393296545L;

    /**
     * 算法
     */
    private String algorithm;

    /**
     * 秘钥
     */
    private String password;

    /**
     * 内容
     */
    private String value;

    /**
     * 应用名称
     */
    private String applicationName;

}
