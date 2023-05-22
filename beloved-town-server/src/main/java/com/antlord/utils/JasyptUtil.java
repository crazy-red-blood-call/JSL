package com.antlord.utils;

import cn.hutool.crypto.SecureUtil;
import com.antlord.bean.JasyptBean;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class JasyptUtil {

    /**
     * 标准加密数据
     * @param param
     * @return
     */
    public static String encryptStr(JasyptBean param) {
        // 1. 创建加解密工具实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

        // 2. 加解密配置
        SimpleStringPBEConfig config = getConfig(param);

        encryptor.setConfig(config);

        // 3. 加密
        return encryptor.encrypt(param.getValue());
    }

    /**
     * 封装配置
     * @param param
     * @return
     */
    private static SimpleStringPBEConfig getConfig(JasyptBean param) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        String password = SecureUtil.md5(SecureUtil.md5(param.getApplicationName() + SecureUtil.md5(param.getPassword())));

        config.setPassword(password);
        config.setAlgorithm(param.getAlgorithm());

        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        return config;
    }


    /**
     * 标准解密数据
     * @param param
     * @return
     */
    public static String decryptStr(JasyptBean param) {
        // 1. 创建加解密工具实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

        // 2. 加解密配置
        SimpleStringPBEConfig config = getConfig(param);
        encryptor.setConfig(config);

        // 3. 解密
        String encryptedText = param.getValue();
        encryptedText = encryptedText.replace("ENC(", "");
        if (encryptedText.endsWith(")")) {
            encryptedText = encryptedText.substring(0, encryptedText.length() - 1);
        }

        return encryptor.decrypt(encryptedText);
    }

    public static void main(String[] args) {
        JasyptBean jasyptBean = new JasyptBean();
        jasyptBean.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        jasyptBean.setApplicationName("beloved-town-server");
        jasyptBean.setPassword("ngHLqLXege3uTgQDnRyFkw==");
        jasyptBean.setValue("Txy#2023");
        String encryptStr = encryptStr(jasyptBean);
        System.out.println("加密前:" + jasyptBean.getValue());
        System.out.println("加密后:" + encryptStr);

        jasyptBean.setValue(encryptStr);
        String decryptStr = decryptStr(jasyptBean);
        System.out.println("解密后:" + decryptStr);
    }
}
