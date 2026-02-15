# cyber-tank-tool-test

用于验证 `RedisService` 与 `MySqlService` 的功能测试模块。

## 前置条件

- MySQL 中存在数据库 `cyber-user-test`
- 目标表结构：

```sql
CREATE TABLE `user` (
  `id` BIGINT NOT NULL,
  `name` VARCHAR(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```

- Redis 可访问

## 执行测试

```bash
mvn -pl cyber-tank-tool-test -Dtest=RedisAndMySqlToolsTest -Dtool.integration.enabled=true test
```

可通过环境变量覆盖连接配置：

- `TOOL_TEST_MYSQL_HOST`
- `TOOL_TEST_MYSQL_PORT`
- `TOOL_TEST_MYSQL_USERNAME`
- `TOOL_TEST_MYSQL_PASSWORD`
- `TOOL_TEST_REDIS_HOST`
- `TOOL_TEST_REDIS_PORT`
- `TOOL_TEST_REDIS_PASSWORD`
