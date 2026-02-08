package com.cyber.tank.member.controller;


import com.alibaba.nacos.api.model.v2.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shushun
 * @date 2026/1/23
 */
@Tag(name = "MemberController", description = "MemberController")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {


    @Operation(summary = "查询分页", description = "通过传入查询参数来得到分页数据")
    @PostMapping("/queryPage")
    public Result<String> getById() {
        return Result.success("Sample Data: " );
    }
}