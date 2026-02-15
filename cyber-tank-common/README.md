# cyber-tank-common 公共模块说明

## 当前公共模块

- `cyber-tank-common-core`：核心能力（统一返回、异常、注解、上下文、基础工具）。
- `cyber-tank-common-cache`：缓存层公共能力（Redis 通用工具、Key 规范构建）。
- `cyber-tank-common-db`：数据库公共能力（MySQL 通用访问工具、批量更新、健康探测）。
- `cyber-tank-common-web`：Web 层公共能力（AOP、全局异常处理、接口日志）。
- `cyber-tank-common-thirdparty`：三方能力聚合（微信/支付宝/短信）。
- `cyber-tank-common-mq`：消息能力聚合（RocketMQ/RabbitMQ）。

## 建议下一步补齐的公共模块

- `cyber-tank-common-idempotent`：接口幂等（令牌 + Redis 去重 + 注解切面）。
- `cyber-tank-common-lock`：分布式锁（统一加解锁模板 + 超时续租）。
- `cyber-tank-common-observability`：观测能力（Metrics、Tracing、日志规范）。
- `cyber-tank-common-test`：测试支撑（Mock 工具、测试基类、数据构造器）。

以上模块可以按业务优先级逐步拆分，避免所有公共代码堆在 core 中导致后期耦合。
