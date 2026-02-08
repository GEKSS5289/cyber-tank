package com.cyber.tank.common.web.aspect;

import com.cyber.tank.common.core.annotation.Log;
import com.cyber.tank.common.core.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 操作日志记录处理
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 处理完请求后执行（正常返回）
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {
            // 1. 获取当前用户（从我们在 Core 写的 SecurityUtils 获取）
            // 2. 获取请求地址、IP
            String url = ServletUtils.getRequest().getRequestURI();
            String method = ServletUtils.getRequest().getMethod();
            
            // 3. 组装日志信息打印输出或异步存入数据库
            log.info(">>>> [日志记录] 标题：{}，方法：{} {}，状态：{}", 
                controllerLog.title(), method, url, e == null ? "成功" : "失败: " + e.getMessage());
            
            // 注意：生产环境下这里通常会发一个 MQ 消息或者开启异步线程存入 db_log 表
        } catch (Exception exp) {
            log.error("日志记录异常: {}", exp.getMessage());
        }
    }
}
