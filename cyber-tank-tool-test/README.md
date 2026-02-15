# cyber-tank-tool-test

用于验证以下工具模块能力：

- `RedisService`
- `MySqlService`
- `RabbitMqService`
- `cyber-tank-common-core` 下常用 `utils`

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
- （可选）RabbitMQ 可访问（当前 Rabbit 测试使用 MockBean，不强依赖真实实例）

## 执行测试

```bash
mvn -pl cyber-tank-tool-test -Dtest=RedisAndMySqlToolsTest,RabbitMqAndCoreUtilsTest -Dtool.integration.enabled=true test
```

可通过环境变量覆盖连接配置：

- `TOOL_TEST_MYSQL_HOST`
- `TOOL_TEST_MYSQL_PORT`
- `TOOL_TEST_MYSQL_USERNAME`
- `TOOL_TEST_MYSQL_PASSWORD`
- `TOOL_TEST_REDIS_HOST`
- `TOOL_TEST_REDIS_PORT`
- `TOOL_TEST_REDIS_PASSWORD`
- `TOOL_TEST_RABBITMQ_HOST`
- `TOOL_TEST_RABBITMQ_PORT`
- `TOOL_TEST_RABBITMQ_USERNAME`
- `TOOL_TEST_RABBITMQ_PASSWORD`
