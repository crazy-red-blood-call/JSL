package com.antlord.config;

import cn.hutool.core.util.StrUtil;
import com.antlord.bean.JwtUserBean;
import com.antlord.entity.SysUser;
import com.antlord.enums.RsultCodeEnum;
import com.antlord.exception.AuthorizationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtConfig {

    @Value("${token.expires}")
    private Integer token;

    @Value("${token.sign}")
    private String sign;
    private static String signs;

    @PostConstruct
    public void init() {
        signs = sign;
    }

    /**
     * 生成token
     */
    public String createToken(JwtUserBean user) {
        Calendar calendar = Calendar.getInstance();
        // 默认令牌过期时间7天
        calendar.add(Calendar.MINUTE, token);
        JWTCreator.Builder builder = JWT.create();
        builder
                .withClaim("accountName", user.getAccountName())
                .withClaim("userId", user.getUserId())
                .withClaim("userName", user.getUserName())
                .withClaim("roleNameList", user.getRoleNameList().stream().collect(Collectors.joining(",")))
                .withClaim("phone", user.getPhone());

        return builder.withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(sign));
    }

    /**
     * 解析token
     */
    public static DecodedJWT verifyToken(String token) {
        if (token == null) {
            return null;
        }
        //获取登录用户真正的密码假如数据库查出来的是123456
        DecodedJWT jwt = null;
        try {
            JWTVerifier build = JWT.require(Algorithm.HMAC256(signs)).build();
            if (token.startsWith("Bearer ")) {
                token = token.replaceFirst("Bearer ", "");
            }
            jwt = build.verify(token);
        }catch (SignatureVerificationException e) {
            log.error("无效签名");
            throw new AuthorizationException(RsultCodeEnum.AUTHORIZATION_SIGN);
        } catch (TokenExpiredException e) {
            log.error("登录过期");
            throw new AuthorizationException(RsultCodeEnum.AUTHORIZATION_EXPIRED);
        } catch (AlgorithmMismatchException e) {
            log.error("token算法不一致");
            throw new AuthorizationException(RsultCodeEnum.AUTHORIZATION_ALGORITHM);
        } catch (Exception e) {
            log.error("token无效");
            throw new AuthorizationException(RsultCodeEnum.AUTHORIZATION_ERROR);
        }
        return jwt;
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static SysUser getUserInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String authorization = request.getHeader("Authorization");
        if (StrUtil.isBlank(authorization)) {
            throw new AuthorizationException(RsultCodeEnum.AUTHORIZATION_BLANK);
        }

        DecodedJWT decodedJWT = verifyToken(authorization);
        if (null == decodedJWT) {
            throw new AuthorizationException(RsultCodeEnum.AUTHORIZATION_EXPIRED);
        }

        Map<String, Claim> claims = decodedJWT.getClaims();
        SysUser sysUser = new SysUser();
        sysUser.setAccountName(claims.get("accountName").asString());
        sysUser.setPhone(claims.get("phone").asString());
        sysUser.setUserName(claims.get("userName").asString());
        sysUser.setUserId(claims.get("userId").asString());
        if (null != claims.get("roleNameList")) {
            String string = claims.get("roleNameList").asString();
            String[] split = string.split(",");
            sysUser.setRoleNameList(Arrays.stream(split).collect(Collectors.toList()));
        }
        return sysUser;
    }

}
