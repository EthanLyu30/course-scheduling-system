# 双向选择排课系统

> 基于学生时间偏好的智能排课平台，支持学生、教师、教务三端协同

## 项目简介

本系统是一个支持双向选择的智能排课平台，通过收集学生和教师的时间偏好与课程意愿，由教务系统统一协调生成最优课表。

### 系统架构

```
├── student-system/     # 学生排课子系统 (端口: 8081/5173)
├── teacher-system/     # 教师排课子系统 (端口: 8082/5174)  
├── admin-system/       # 教务协调子系统 (端口: 8083/5175)
└── database/           # 共享数据库脚本
```

## 技术栈

| 端 | 技术 |
|---|------|
| 前端 | Vue 3 + Vite + Element Plus + Pinia |
| 后端 | Spring Boot 3 + MyBatis-Plus + Knife4j |
| 数据库 | MySQL 8.0 |

## 快速开始

### 环境要求

- JDK 17+
- Node.js 18+
- MySQL 8.0+

### 1. 克隆项目

```bash
git clone https://github.com/你的用户名/course-scheduling-system.git
cd course-scheduling-system
```

### 2. 初始化数据库

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/init-data.sql
```

### 3. 启动学生子系统

```bash
# 后端
cd student-system/student-backend
./mvnw spring-boot:run

# 前端（新终端）
cd student-system/student-frontend
npm install
npm run dev
```

### 4. 访问系统

- 前端页面: http://localhost:5173
- 接口文档: http://localhost:8081/doc.html

## 项目文档

- [项目实施指南](./docs/项目实施指南.md)
- [概要设计文档](./docs/概要设计.md)

## 作者

- 吕霄阳 (2327406032)
- 苏州大学 软件系统分析与设计课程
