package com.cyber.tank.member.controller;


import com.cyber.tank.common.core.annotation.Log;
import com.cyber.tank.common.core.domain.Result;
import com.cyber.tank.common.core.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 成员管理接口
 * * 规范点：
 * 1. 使用 @Tag 标注 Swagger 文档
 * 2. 使用 @Slf4j 记录日志
 * 3. 使用 @RequiredArgsConstructor 代替 @Autowired 进行构造器注入
 * 4. 路径遵循 Restful 风格
 */
@Tag(name = "测试管理", description = "测试")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class TestController {

    @Operation(summary = "根据ID获取用户信息", description = "传入用户长整型ID，返回脱敏后的用户信息")
    @GetMapping("/{id}")
    @Log(title = "查询会员详情")
    public Result<String> getUser(
            @PathVariable("id") @Parameter(description = "用户唯一ID", required = true) Integer id
    ) {
        if (id == 1) {
            throw new ServiceException("我死了");
        }

        return Result.ok("User Data for " + id);
    }

}
