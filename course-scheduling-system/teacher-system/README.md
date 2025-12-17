# 教师排课子系统（Teacher System）

- 后端：Spring Boot 3 + MyBatis-Plus + Knife4j
- 端口：8082
- 功能骨架：教师偏好维护、教师课表查询、约束规则查看、冲突反馈占位
- 对齐：与学生子系统统一使用 `course_scheduling` 库与核心表（preference/constraint_rule/scheduling_plan/course_assignment/conflict_record 等）

## 快速开始

```powershell
cd teacher-system/teacher-backend
mvn clean package -DskipTests
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

关键配置：`src/main/resources/application-dev.yml`
- DB: `spring.datasource.url/username/password`
- 端口: `server.port=8082`
- 文档: `/doc.html`

## 目录
- config: CORS、MyBatis-Plus、Knife4j
- controller: 偏好、课表、约束、冲突占位
- service + impl: 业务骨架，当前为最小实现
- entity/mapper: 复用公共表结构，便于教务协调子系统对接
- vo/dto: 通用返回结果、课表视图、偏好/冲突传输对象

## 待完善
- 登录鉴权与教师身份校验
- 偏好 UPSERT、版本控制、增量消息处理
- 课表确认的事务逻辑与教务系统联动
- 冲突处理与建议、与教务协调子系统的消息交互
- 单元/集成测试
