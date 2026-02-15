package com.cyber.tank.tooltest.test;

import com.cyber.tank.common.cache.utils.RedisService;
import com.cyber.tank.common.db.utils.MySqlService;
import com.cyber.tank.tooltest.config.ToolIntegrationTestApplication;
import com.cyber.tank.tooltest.entity.ToolTestUser;
import com.cyber.tank.tooltest.mapper.ToolTestUserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 运行方式：
 * mvn -pl cyber-tank-tool-test -Dtest=RedisAndMySqlToolsTest -Dtool.integration.enabled=true test
 */
@SpringBootTest(classes = ToolIntegrationTestApplication.class)
@ActiveProfiles("test")
@EnabledIfSystemProperty(named = "tool.integration.enabled", matches = "true")
/**
 * RedisAndMySqlToolsTest 的核心定义。
 */
class RedisAndMySqlToolsTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private MySqlService mySqlService;

    @Autowired
    private ToolTestUserMapper userMapper;

    @Test
    void shouldOperateRedisByRedisService() {
        String key = "tool:test:redis:key";
        String hashKey = "tool:test:redis:hash";

        redisService.setObject(key, "hello");
        assertEquals("hello", redisService.getObject(key));
        assertTrue(redisService.hasKey(key));

        redisService.expire(key, 120, TimeUnit.SECONDS);
        assertTrue(redisService.getExpire(key) > 0);

        long first = redisService.increment("tool:test:redis:counter");
        long second = redisService.increment("tool:test:redis:counter");
        assertEquals(first + 1, second);

        redisService.putHashAll(hashKey, Map.of("id", 1L, "name", "cyber"));
        assertEquals("cyber", redisService.getHashValue(hashKey, "name"));

        assertTrue(redisService.deleteObject(key));
        assertTrue(redisService.deleteObjects(java.util.List.of(hashKey, "tool:test:redis:counter")) >= 1);
    }

    @Test
    void shouldOperateMySqlByMySqlService() {
        long id = System.currentTimeMillis();

        ToolTestUser user = new ToolTestUser();
        user.setId(id);
        user.setName("cyber-user");

        assertEquals(1, mySqlService.insert(userMapper, user));

        ToolTestUser dbUser = mySqlService.selectById(userMapper, id);
        assertNotNull(dbUser);
        assertEquals("cyber-user", dbUser.getName());

        dbUser.setName("cyber-user-updated");
        assertEquals(1, mySqlService.updateById(userMapper, dbUser));

        long count = mySqlService.count(userMapper, wrapper -> wrapper.eq(ToolTestUser::getId, id));
        assertEquals(1L, count);

        assertEquals(1, mySqlService.deleteById(userMapper, id));
        assertNull(mySqlService.selectById(userMapper, id));
    }
}
