# cyber-tank-common 公共工件说明

## 一、设计目标

`cyber-tank-common` 的目标不是把所有能力塞进一个大包，而是把“新服务默认需要的能力”和“按需加载的能力”拆开。

这样做有两个直接收益：

- 新服务接入简单，直接引用基础 Starter 就能启动
- 能力边界清晰，缓存、数据库、消息等不会被无关服务误带入
- 安全能力集中治理，后续扩展 JWT、网关鉴权和内部调用规则时不再到处改业务模块

## 二、推荐依赖方式

### 1. 普通业务服务

默认只引：

- `cyber-tank-common-starter`

该 Starter 当前内置：

- `cyber-tank-common-starter-web`
- `cyber-tank-common-starter-security`
- `spring-cloud-starter-alibaba-nacos-discovery`
- `spring-cloud-starter-alibaba-nacos-config`
- `spring-cloud-starter-bootstrap`

对应基础能力：

- MVC、统一返回、全局异常
- JWT 配置初始化
- Feign 认证头与 TraceId 透传
- 内部调用鉴权注解支持

### 2. 网关服务

默认引：

- `cyber-tank-common-starter-gateway`

该 Starter 当前内置：

- `cyber-tank-common-gateway`
- `cyber-tank-common-starter-security`
- `spring-cloud-starter-loadbalancer`
- `spring-cloud-starter-alibaba-nacos-discovery`
- `spring-cloud-starter-alibaba-nacos-config`
- `spring-cloud-starter-bootstrap`

对应基础能力：

- JWT 鉴权过滤器
- 用户头与内部头清洗
- TraceId 透传
- CORS 基础配置
- 常用限流 `KeyResolver`

### 3. 按需扩展能力

- 缓存能力：`cyber-tank-common-starter-cache`
- 数据库能力：`cyber-tank-common-starter-db`
- RabbitMQ 能力：`cyber-tank-common-starter-rabbit`

## 三、模块分层

- `cyber-tank-common-core`
  - 统一返回、异常、链路追踪常量、基础工具
- `cyber-tank-common-security`
  - JWT、用户上下文、内部鉴权、Feign 认证透传
- `cyber-tank-common-web`
  - MVC、AOP、异常处理、参数校验、文档支持
- `cyber-tank-common-gateway`
  - 网关鉴权、响应式 Trace、CORS、限流键
- `cyber-tank-common-cache`
  - Redis 模板与缓存工具
- `cyber-tank-common-db`
  - MyBatis-Plus 与 MySQL 公共能力
- `cyber-tank-common-mq`
  - 消息中间件能力聚合
- `cyber-tank-common-thirdparty`
  - 外部平台能力聚合

## 四、维护规范

- `starter` 只负责装配依赖与自动接入，不承载复杂业务代码
- `core` 保持轻量，避免继续堆入安全、缓存、数据库、消息等重能力
- `security` 统一承接 JWT、登录态和内部调用规则，业务模块不要重复写认证透传
- `gateway` 相关公共过滤器优先下沉到 `cyber-tank-common-gateway`，不要回流到网关宿主模块
- 新公共能力优先独立成模块，再决定是否通过 starter 暴露
