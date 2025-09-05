# Compare Demo

一个基于Spring Boot的比较演示项目。

## 项目信息

- **项目名称**: compare-demo
- **Java版本**: 1.8
- **Spring Boot版本**: 2.7.18
- **构建工具**: Maven

## 技术栈

- Spring Boot Web
- Spring Data JPA
- H2 Database
- Spring Boot Actuator

## 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+

### 运行项目

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run

# 或者打包后运行
mvn clean package
java -jar target/compare-demo-1.0.0-SNAPSHOT.jar
```

### 访问地址

- 应用地址: http://localhost:8080/api
- H2控制台: http://localhost:8080/api/h2-console
- 健康检查: http://localhost:8080/api/actuator/health

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/compare/demo/
│   │       └── CompareApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/compare/demo/
```

## 配置说明

### 主要配置项

- `server.port`: 服务端口 (8080)
- `server.servlet.context-path`: 应用上下文路径 (/api)
- `spring.h2.console.enabled`: 启用H2数据库控制台
- `logging.level.com.compare.demo`: 应用日志级别 (DEBUG)

### 数据库配置

项目使用H2内存数据库，适合开发和测试环境。

## 开发指南

### 添加新的Controller

在 `com.compare.demo.controller` 包下创建新的Controller类。

### 添加新的Service

在 `com.compare.demo.service` 包下创建新的Service类。

### 添加新的Entity

在 `com.compare.demo.entity` 包下创建新的Entity类。

## 提交规范

请使用语义化的提交信息格式：

```
<type>(<scope>): <subject>

<body>

<footer>
```

类型说明：
- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档更新
- `style`: 代码格式化
- `refactor`: 重构
- `test`: 测试相关
- `chore`: 构建过程或辅助工具的变动

## 许可证

MIT License