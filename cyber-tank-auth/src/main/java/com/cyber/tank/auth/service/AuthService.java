package com.cyber.tank.auth.service;

import com.cyber.tank.api.RemoteMemberService;
import com.cyber.tank.common.core.constant.SecurityConstants;
import com.cyber.tank.common.core.domain.Result;
import com.cyber.tank.common.core.exception.ServiceException;
import com.cyber.tank.common.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
/**
 * AuthService 的核心定义。
 */
public class AuthService {

    private final RemoteMemberService remoteMemberService;

    /**
     * login 方法。
     * @param username 参数。
     * @param password 参数。
     * @return 执行结果。
     */
    public String login(String username, String password) {
        // 1. 获取用户信息
        Map<String, Object> member = loadMember(username);

        // 2. 验证密码
        validatePassword(password, String.valueOf(member.get("password")));

        // 3. 签发令牌
        return createToken(member);
    }

    /**
     * loadMember 方法。
     * @param username 参数。
     * @return 执行结果。
     */
    private Map<String, Object> loadMember(String username) {
        Result<Map<String, Object>> result = remoteMemberService.getUserInfo();
        if (result == null || result.getData() == null) {
            throw new ServiceException("用户不存在");
        }
        return result.getData();
    }

    /**
     * validatePassword 方法。
     * @param inputPassword 参数。
     * @param dbPassword 参数。
     */
    private void validatePassword(String inputPassword, String dbPassword) {
        // 目前先做明文比对，后续建议引入 BCrypt
        if (!inputPassword.equals(dbPassword)) {
            throw new ServiceException("用户名或密码错误");
        }
    }

    /**
     * createToken 方法。
     * @param member 参数。
     * @return 执行结果。
     */
    private String createToken(Map<String, Object> member) {
        Map<String, Object> claims = new HashMap<>();
        // 这里的 Key 必须和 AuthFilter 解析时的 Key 保持绝对一致
        claims.put(SecurityConstants.DETAILS_USER_ID, member.get("userId"));
        claims.put(SecurityConstants.DETAILS_USERNAME, member.get("username"));

        return JwtUtils.createToken(claims);
    }
}