package com.cyber.tank.member.controller;

import com.cyber.tank.common.core.domain.Result;
import com.cyber.tank.member.model.MemberProfile;
import com.cyber.tank.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 会员查询接口。
 */
@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/current")
    public Result<MemberProfile> currentMember() {
        return Result.ok(memberService.currentMember());
    }

    @GetMapping
    public Result<List<MemberProfile>> listMembers() {
        return Result.ok(memberService.listMembers());
    }
}
