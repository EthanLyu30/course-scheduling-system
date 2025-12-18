# 双向选择排课系统

> 基于学生时间偏好的智能排课平台，目前仓库包含“学生子系统（后端 + 前端）”与公共数据库脚本，教师/教务子系统留作后续扩展。

## 项目简介

本系统收集学生时间偏好与课程意愿，提供课程选择、意愿排序与排课结果查看，并支持反馈申诉。

### 目录概览

```
├── database/                 # MySQL schema & init data（含新增时间片/偏好/方案/冲突表及索引）
├── student-system/
│   ├── student-backend/      # Spring Boot 3 (端口 8081)
│   └── student-frontend/     # Vue 3 + Vite (端口 5173)
└── teacher-system/
	├── teacher-backend/      # Spring Boot 3 (端口 8082)
	└── teacher-frontend/     # Vue 3 + Vite (端口 5174)
```

## 技术栈

| 端       | 技术                              |
|---------|-----------------------------------|
| 前端    | Vue 3 + Vite + Element Plus + Pinia |
| 后端    | Spring Boot 3 + MyBatis-Plus + Knife4j |
| 数据库  | MySQL 8.0 |

## 快速开始（Windows / PowerShell）

### 0. 环境要求

- Git、JDK 17+、Maven 3.9+（若无 mvnw 包装器）、Node.js 18+、MySQL 8.0+

### 1. 克隆项目

```powershell
git clone https://github.com/EthanLyu30/course-scheduling-system.git
cd course-scheduling-system
```

### 2. 初始化数据库

默认库名 `course_scheduling`，如需修改请同步调整 `student-backend/src/main/resources/application-dev.yml`。

```powershell
mysql -u root -p -e "source database/schema.sql"
mysql -u root -p -e "source database/init-data.sql"
```

### 3. 启动学生子系统后端（8081）

```powershell
cd student-system/student-backend
mvn clean package -DskipTests
mvn --% spring-boot:run -Dspring-boot.run.profiles=dev
```

关键配置：`application-dev.yml`
- DB 连接：`spring.datasource.url` / `username` / `password`
- 端口：`server.port=8081`
- Swagger/Knife4j：`/doc.html`

### 4. 启动学生子系统前端（5173）

```powershell
cd student-system/student-frontend
npm install
npm run dev -- --host
```

前端环境变量：`.env.development`
- `VITE_APP_BASE_API=http://localhost:8081/api`

### 5. 访问

- 前端页面：http://localhost:5173
- 接口文档：http://localhost:8081/doc.html

### 6. 启动教师子系统后端（8082）

```powershell
cd teacher-system/teacher-backend
mvn clean package -DskipTests
mvn --% spring-boot:run -Dspring-boot.run.profiles=dev
```

新增表与索引：time_slot、preference（多态偏好，含唯一约束）、constraint_rule、scheduling_plan、course_assignment、conflict_record。

### 7. 启动教师子系统前端（5174）

```powershell
cd teacher-system/teacher-frontend
npm install
npm run dev -- --host
```

教师端登录：调用 `/api/auth/login`（简单口令校验，返回 token / teacherId）。前端默认以工号+密码登录，token 写入 localStorage。

### 8. 生产构建（可选）

```powershell
# 后端可执行包
cd student-system/student-backend
mvn clean package -DskipTests
# 生成 jar 位于 target/ ，可使用 `java -jar target/*.jar --spring.profiles.active=dev` 运行

# 前端静态资源
cd ../student-frontend
npm run build
# 产物在 dist/ ，可挂到任意静态服务器，或由网关/后端托管
```

## 其他说明

- 示例登录：前端内置“模拟登录”页面，默认使用 `studentId=1` 写入 localStorage。
- 当前仓库未包含教师/教务子系统代码，后续可按相同结构补充 `teacher-system/`、`admin-system/`。

## 作者

- 吕霄阳 (2327406032)
- 苏州大学 软件系统分析与设计课程
