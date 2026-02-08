package com.cyber.tank.common.core.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.cyber.tank.common.core.constant.SecurityConstants;
import com.cyber.tank.common.core.exception.ServiceException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * JWT 工具类 (基于 nimbus-jose-jwt)
 * 扩展功能：Bearer 处理、过期校验、便捷获取 UserID
 */
@Slf4j
public class JwtUtils {

    // 建议后续将密钥移至配置文件 (yml) 中管理
    private static final String SECRET = "CyberTank-Modern-Security-Key-2026-666888";

    // 默认过期时间 (例如 24 小时)
    private static final long DEFAULT_EXPIRE_MILLIS = 24 * 60 * 60 * 1000L;

    /**
     * 创建令牌 (使用默认过期时间)
     */
    public static String createToken(Map<String, Object> claims) {
        return createToken(claims, DEFAULT_EXPIRE_MILLIS);
    }

    /**
     * 创建令牌 (指定过期时间)
     *
     * @param claims       载荷
     * @param expireMillis 过期时间（毫秒）
     */
    public static String createToken(Map<String, Object> claims, long expireMillis) {
        try {
            long now = System.currentTimeMillis();
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .issueTime(new Date(now)) // 签发时间
                    .expirationTime(new Date(now + expireMillis)) // 过期时间
                    // 将 Map 中的 claims 批量放入
                    .claim(SecurityConstants.DETAILS_USER_ID, claims.get(SecurityConstants.DETAILS_USER_ID))
                    .claim(SecurityConstants.DETAILS_USERNAME, claims.get(SecurityConstants.DETAILS_USERNAME))
                    // 如果有其他自定义 KV，也可以遍历放入，这里为了演示明确列出
                    .build();

            // 如果 claims 里有其他字段，可以通过 customBuilder 补充
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder(claimsSet);
            claims.forEach((k, v) -> {
                if (!builder.getClaims().containsKey(k)) {
                    builder.claim(k, v);
                }
            });

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), builder.build());
            signedJWT.sign(new MACSigner(SECRET));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error("生成令牌失败: {}", e.getMessage());
            throw new ServiceException("生成令牌失败");
        }
    }

    /**
     * 解析令牌获取所有声明 (Claims)
     * 包含签名校验和格式校验
     */
    public static Map<String, Object> parseToken(String token) {
        JWTClaimsSet claimsSet = parseClaimsSet(token);
        return claimsSet.getClaims();
    }

    /**
     * 获取用户 ID (安全转换)
     */
    public static Long getUserId(String token) {
        Object value = getClaim(token, SecurityConstants.DETAILS_USER_ID);
        return Convert.toLong(value);
    }

    /**
     * 获取用户名
     */
    public static String getUserName(String token) {
        Object value = getClaim(token, SecurityConstants.DETAILS_USERNAME);
        return Convert.toStr(value);
    }

    /**
     * 获取指定 Claim
     */
    public static Object getClaim(String token, String claimKey) {
        try {
            JWTClaimsSet claimsSet = parseClaimsSet(token);
            return claimsSet.getClaim(claimKey);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验令牌是否有效 (签名正确且未过期)
     */
    public static boolean validateToken(String token) {
        try {
            JWTClaimsSet claimsSet = parseClaimsSet(token);
            Date expireTime = claimsSet.getExpirationTime();
            // 如果没设置过期时间，视为不过期；否则判断是否已过期
            return expireTime == null || expireTime.after(new Date());
        } catch (Exception e) {
            log.warn("令牌校验不通过: {}", e.getMessage());
            return false;
        }
    }

    // ================= 私有辅助方法 =================

    /**
     * 核心解析逻辑：处理前缀、验签、返回 ClaimsSet 对象
     */
    private static JWTClaimsSet parseClaimsSet(String token) {
        // 1. 处理 Bearer 前缀
        String realToken = StrUtil.removePrefixIgnoreCase(token, SecurityConstants.TOKEN_PREFIX).trim();

        if (StrUtil.isBlank(realToken)) {
            throw new ServiceException("令牌为空");
        }

        try {
            SignedJWT signedJWT = SignedJWT.parse(realToken);

            // 2. 校验签名
            if (!signedJWT.verify(new MACVerifier(SECRET))) {
                throw new ServiceException("令牌签名无效");
            }

            // 3. 校验是否过期 (可选，如果 parseToken 只是为了拿数据可以不强制抛错，视业务而定)
            // 这里我们做严格模式：过期也算解析异常
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expirationTime != null && expirationTime.before(new Date())) {
                throw new ServiceException("令牌已过期");
            }

            return signedJWT.getJWTClaimsSet();
        } catch (ParseException | JOSEException e) {
            log.error("令牌解析异常: {}", e.getMessage());
            throw new ServiceException("无效的令牌");
        }
    }
}