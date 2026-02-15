package com.cyber.tank.auth.controller;

import com.cyber.tank.auth.dto.UserLoginDTO;
import com.cyber.tank.auth.service.AuthService;
import com.cyber.tank.common.core.domain.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
/**
 * AuthController 的核心定义。
 */
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    /**
     * login 方法。
     * @param loginForm 参数。
     * @return 执行结果。
     */
    public Result<String> login(@Validated @RequestBody UserLoginDTO loginForm) {
        String token = authService.login(loginForm.getUsername(), loginForm.getPassword());
        return Result.ok(token);
    }
}