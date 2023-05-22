package com.antlord.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import com.antlord.enums.RsultCodeEnum;
import com.antlord.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * 接口参数验签拦截器
 */
@Service
@Slf4j
public class CipherInterceptor implements InterceptorBase {

    /**
     * 接口加密秘钥
     */
    @Value("${cipher.md5Key}")
    private String md5Key;

    /**
     * 是否开启接口加密
     */
    @Value("${cipher.enable:true}")
    private boolean enable;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (true) {
            return true;
        }
        // 验证请求体数据
        return checkCipher(request, response);
    }

    @Override
    public int getSort() {
        return 1;
    }

    /**
     * 验证密文
     * @param request
     * @param response
     * @return
     */
    private boolean checkCipher(HttpServletRequest request, HttpServletResponse response) {
        try {
            String cipher = request.getHeader("cipher");
            if (StrUtil.isBlank(cipher)) {
                response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                log.info("====AuthInterceptor.checkCipher.md5.check.cipher.is.blank====");
                ResponseUtil.resultMsg(response, RsultCodeEnum.CIPHER_IS_BLANK);
                return false;
            }
            StringBuilder plaintext = new StringBuilder();
            // 对请求体加密
            String body = request.getReader().lines().collect(Collectors.joining());
            if (StrUtil.isNotBlank(body)) {
                plaintext.append(body.replace("\"", ""));
            }

            // 对请求url中的参数加密
            String queryString = request.getQueryString();
            if (StrUtil.isNotBlank(queryString)) {
                // 转码
                queryString = URLUtil.decode(queryString);
                plaintext.append(queryString);
            }

            // 对请求url体加密
            String url = request.getRequestURI();
            plaintext.append(url.replaceFirst("//", "/"));
            return check(plaintext.toString(), cipher, response);
        } catch (Exception e) {
            log.error("AuthInterceptor.checkCipher.error:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 验证加密
     * @param plaintext 请求参数
     * @param cipher 传入密文
     * @param response
     * @return
     */
    private boolean check(String plaintext, String cipher, HttpServletResponse response) {
        // 使用MD5加密
        String encryption = SecureUtil.md5(plaintext + md5Key);
        log.info("--->plaintext:{},encryption:{},cipher:{}", plaintext + md5Key, encryption, cipher);
        // 验证密文与加密是否一致，若一致则通过，若不一致则打回
        if (!cipher.equals(encryption)) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            log.info("------->  AuthInterceptor.checkCipher.md5.check.not pass");
            log.info("===> plaintext:{},cipher:{}", plaintext, cipher);
            ResponseUtil.resultMsg(response, RsultCodeEnum.CIPHER_IS_FAILED);
            return false;
        }
        return true;
    }

}
