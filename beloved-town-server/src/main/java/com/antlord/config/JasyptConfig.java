package com.antlord.config;


import cn.hutool.core.util.StrUtil;
import com.antlord.bean.JasyptBean;
import com.antlord.utils.JasyptUtil;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("encryptablePropertyResolver")
@Slf4j
public class JasyptConfig implements EncryptablePropertyResolver {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${jasypt.algorithm}")
    private String algorithm;

    @Value("${jasypt.password}")
    private String passwords;

    @Override
    public String resolvePropertyValue(String property) {
        try {
            if (StrUtil.isNotBlank(property) && property.startsWith("ENC(")) {
                JasyptBean param = new JasyptBean();
                param.setApplicationName(applicationName);
                param.setPassword(passwords);
                param.setValue(property);
                param.setAlgorithm(algorithm);

                return JasyptUtil.decryptStr(param);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return property;
    }
}
