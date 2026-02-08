package com.cyber.tank.api;


import com.cyber.tank.common.core.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

// contextId 保证唯一，value 指向 Nacos 中的服务名
@FeignClient(contextId = "remoteMemberService", value = "cyber-tank-business-member")
public interface RemoteMemberService {
    @GetMapping("/v1/members/inner")
    Result<Map<String, Object>> getUserInfo();
}