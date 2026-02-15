package com.cyber.tank.member.controller;

import com.cyber.tank.common.core.domain.Result;
import com.cyber.tank.common.core.exception.ServiceException;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberInnerController {

    @Resource
    private RedisService redisService;
//    @InnerAuth(isUser = true)
    @GetMapping("/inner")
    public Result<Map<String, Object>> getUserInfo() {
        Object shushhun = redisService.getObject("shushhun");
        log.info("{}",shushhun);
        throw new ServiceException("wosile");
//        // 模拟数据库查询
//        Map<String, Object> user = new HashMap<>();
//        user.put("userId", 1001);
//        user.put("username", "admin");
//        user.put("password", "123456"); // 实际应为加密密码
//        log.info("{}", SecurityContextHolder.getUserId());
//        return Result.ok(user);
    }
}