-- ============================================
-- 双向选择排课系统 - 数据库建表脚本
-- 创建时间: 2024-12
-- ============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS course_scheduling
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE course_scheduling;

-- ============================================
-- 1. 基础信息表（教务系统维护）
-- ============================================

-- 学生表
CREATE TABLE IF NOT EXISTS student (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    student_no VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender TINYINT DEFAULT 0 COMMENT '性别: 0-未知, 1-男, 2-女',
    major VARCHAR(100) COMMENT '专业',
    grade VARCHAR(20) COMMENT '年级',
    class_name VARCHAR(50) COMMENT '班级',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    password VARCHAR(100) NOT NULL COMMENT '密码（加密存储）',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_student_no (student_no),
    INDEX idx_major_grade (major, grade)
) COMMENT '学生表';

-- 教师表
CREATE TABLE IF NOT EXISTS teacher (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    teacher_no VARCHAR(20) NOT NULL UNIQUE COMMENT '工号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender TINYINT DEFAULT 0 COMMENT '性别: 0-未知, 1-男, 2-女',
    department VARCHAR(100) COMMENT '所属院系',
    title VARCHAR(50) COMMENT '职称',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    password VARCHAR(100) NOT NULL COMMENT '密码（加密存储）',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_teacher_no (teacher_no),
    INDEX idx_department (department)
) COMMENT '教师表';

-- 课程表
CREATE TABLE IF NOT EXISTS course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    course_code VARCHAR(20) NOT NULL UNIQUE COMMENT '课程代码',
    name VARCHAR(100) NOT NULL COMMENT '课程名称',
    credits DECIMAL(3,1) NOT NULL COMMENT '学分',
    hours INT NOT NULL COMMENT '学时',
    course_type TINYINT DEFAULT 1 COMMENT '课程类型: 1-必修, 2-选修, 3-实验, 4-体育',
    description TEXT COMMENT '课程描述',
    syllabus_url VARCHAR(255) COMMENT '教学大纲链接',
    textbook VARCHAR(255) COMMENT '教材',
    assessment VARCHAR(100) COMMENT '考核方式',
    capacity INT DEFAULT 50 COMMENT '容量上限',
    prerequisite_ids VARCHAR(255) COMMENT '先修课程ID（逗号分隔）',
    teacher_id BIGINT COMMENT '授课教师ID',
    semester VARCHAR(20) NOT NULL COMMENT '学期（如: 2024-2025-1）',
    popularity_index DECIMAL(5,2) DEFAULT 0 COMMENT '选课热度指数',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-停开, 1-正常',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_course_code (course_code),
    INDEX idx_semester (semester),
    INDEX idx_teacher (teacher_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(id)
) COMMENT '课程表';

-- 教室表
CREATE TABLE IF NOT EXISTS classroom (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    room_code VARCHAR(20) NOT NULL UNIQUE COMMENT '教室编号',
    building VARCHAR(50) NOT NULL COMMENT '教学楼',
    room_name VARCHAR(50) COMMENT '教室名称',
    capacity INT NOT NULL COMMENT '座位数',
    room_type TINYINT DEFAULT 1 COMMENT '类型: 1-普通教室, 2-多媒体, 3-实验室, 4-机房',
    has_projector TINYINT DEFAULT 1 COMMENT '是否有投影仪',
    has_air_conditioner TINYINT DEFAULT 1 COMMENT '是否有空调',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-维修, 1-可用',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_building (building),
    INDEX idx_capacity (capacity)
) COMMENT '教室表';

-- ============================================
-- 2. 学生端表
-- ============================================

-- 学生时间偏好表
CREATE TABLE IF NOT EXISTS student_preference (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    version INT DEFAULT 1 COMMENT '版本号',
    is_current TINYINT DEFAULT 1 COMMENT '是否当前生效: 0-历史, 1-当前',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_student_semester (student_id, semester),
    FOREIGN KEY (student_id) REFERENCES student(id)
) COMMENT '学生时间偏好表';

-- 学生时间段偏好明细表
CREATE TABLE IF NOT EXISTS student_time_slot (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    preference_id BIGINT NOT NULL COMMENT '偏好ID',
    day_of_week TINYINT NOT NULL COMMENT '星期: 1-7代表周一到周日',
    start_time TIME NOT NULL COMMENT '开始时间',
    end_time TIME NOT NULL COMMENT '结束时间',
    preference_level TINYINT NOT NULL COMMENT '偏好等级: 1-不可用, 2-不喜欢, 3-一般, 4-强烈喜欢',
    is_exception TINYINT DEFAULT 0 COMMENT '是否临时例外: 0-否, 1-是',
    exception_date DATE COMMENT '例外日期（仅当is_exception=1时有效）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_preference (preference_id),
    INDEX idx_day_time (day_of_week, start_time),
    FOREIGN KEY (preference_id) REFERENCES student_preference(id) ON DELETE CASCADE
) COMMENT '学生时间段偏好明细表';

-- 偏好模板表
CREATE TABLE IF NOT EXISTS preference_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(50) NOT NULL COMMENT '模板名称',
    description VARCHAR(200) COMMENT '模板描述',
    template_data JSON NOT NULL COMMENT '模板数据（JSON格式）',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统预设: 0-用户自定义, 1-系统预设',
    created_by BIGINT COMMENT '创建者ID（用户自定义时）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '偏好模板表';

-- 学生选课意愿表
CREATE TABLE IF NOT EXISTS student_wish (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    priority TINYINT NOT NULL COMMENT '优先级: 1-一般想选, 2-强烈想选, 3-必选',
    reason VARCHAR(100) COMMENT '选课理由（最多100字）',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-待提交, 1-已提交, 2-已匹配, 3-未匹配',
    submitted_at DATETIME COMMENT '提交时间',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_student_course_semester (student_id, course_id, semester),
    INDEX idx_student_semester (student_id, semester),
    INDEX idx_course (course_id),
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
) COMMENT '学生选课意愿表';

-- 课程评价表
CREATE TABLE IF NOT EXISTS course_review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    semester VARCHAR(20) NOT NULL COMMENT '评价学期',
    content_rating TINYINT COMMENT '教学内容评分: 1-5',
    teaching_rating TINYINT COMMENT '授课水平评分: 1-5',
    workload_rating TINYINT COMMENT '作业负担评分: 1-5（5表示轻松）',
    overall_rating TINYINT NOT NULL COMMENT '综合评分: 1-5',
    comment VARCHAR(500) COMMENT '评价内容',
    is_anonymous TINYINT DEFAULT 1 COMMENT '是否匿名: 0-否, 1-是',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_course (course_id),
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (student_id) REFERENCES student(id)
) COMMENT '课程评价表';

-- ============================================
-- 3. 教师端表
-- ============================================

-- 教师时间偏好表
CREATE TABLE IF NOT EXISTS teacher_preference (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    teacher_id BIGINT NOT NULL COMMENT '教师ID',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    version INT DEFAULT 1 COMMENT '版本号',
    is_current TINYINT DEFAULT 1 COMMENT '是否当前生效',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_teacher_semester (teacher_id, semester),
    FOREIGN KEY (teacher_id) REFERENCES teacher(id)
) COMMENT '教师时间偏好表';

-- 教师时间段偏好明细表
CREATE TABLE IF NOT EXISTS teacher_time_slot (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    preference_id BIGINT NOT NULL COMMENT '偏好ID',
    day_of_week TINYINT NOT NULL COMMENT '星期: 1-7',
    start_time TIME NOT NULL COMMENT '开始时间',
    end_time TIME NOT NULL COMMENT '结束时间',
    preference_level TINYINT NOT NULL COMMENT '偏好等级: 1-不可用, 2-不喜欢, 3-一般, 4-强烈喜欢',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_preference (preference_id),
    FOREIGN KEY (preference_id) REFERENCES teacher_preference(id) ON DELETE CASCADE
) COMMENT '教师时间段偏好明细表';

-- 教师授课意愿表
CREATE TABLE IF NOT EXISTS teacher_wish (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    teacher_id BIGINT NOT NULL COMMENT '教师ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    preferred_classroom_ids VARCHAR(255) COMMENT '偏好教室ID（逗号分隔）',
    max_students INT COMMENT '期望最大学生数',
    note VARCHAR(200) COMMENT '备注',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-待确认, 1-已确认',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_teacher_course_semester (teacher_id, course_id, semester),
    FOREIGN KEY (teacher_id) REFERENCES teacher(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
) COMMENT '教师授课意愿表';

-- ============================================
-- 4. 教务协调表
-- ============================================

-- 排课结果表
CREATE TABLE IF NOT EXISTS schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    teacher_id BIGINT NOT NULL COMMENT '教师ID',
    classroom_id BIGINT COMMENT '教室ID',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    day_of_week TINYINT NOT NULL COMMENT '星期: 1-7',
    start_time TIME NOT NULL COMMENT '开始时间',
    end_time TIME NOT NULL COMMENT '结束时间',
    start_week INT DEFAULT 1 COMMENT '起始周',
    end_week INT DEFAULT 16 COMMENT '结束周',
    week_type TINYINT DEFAULT 0 COMMENT '周类型: 0-每周, 1-单周, 2-双周',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-待发布, 1-已发布, 2-已调整',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_course (course_id),
    INDEX idx_teacher (teacher_id),
    INDEX idx_classroom (classroom_id),
    INDEX idx_semester (semester),
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(id),
    FOREIGN KEY (classroom_id) REFERENCES classroom(id)
) COMMENT '排课结果表';

-- 学生选课结果表
CREATE TABLE IF NOT EXISTS student_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    schedule_id BIGINT NOT NULL COMMENT '排课ID',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    match_status TINYINT DEFAULT 1 COMMENT '匹配状态: 1-已确定, 2-待协调, 3-冲突中, 4-未匹配',
    match_reason VARCHAR(200) COMMENT '匹配/未匹配原因',
    preference_match_rate DECIMAL(5,2) COMMENT '时间偏好满足率',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_student_schedule (student_id, schedule_id),
    INDEX idx_student_semester (student_id, semester),
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (schedule_id) REFERENCES schedule(id)
) COMMENT '学生选课结果表';

-- 排课变更记录表
CREATE TABLE IF NOT EXISTS schedule_change_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    schedule_id BIGINT NOT NULL COMMENT '排课ID',
    change_type VARCHAR(20) NOT NULL COMMENT '变更类型: TIME-时间, ROOM-教室, TEACHER-教师, CANCEL-取消',
    old_value VARCHAR(200) COMMENT '原值',
    new_value VARCHAR(200) COMMENT '新值',
    reason VARCHAR(200) COMMENT '变更原因',
    operator_id BIGINT COMMENT '操作人ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '变更时间',
    INDEX idx_schedule (schedule_id),
    FOREIGN KEY (schedule_id) REFERENCES schedule(id)
) COMMENT '排课变更记录表';

-- ============================================
-- 5. 反馈与通知表
-- ============================================

-- 用户反馈表
CREATE TABLE IF NOT EXISTS feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    user_type TINYINT NOT NULL COMMENT '用户类型: 1-学生, 2-教师',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    overall_rating TINYINT NOT NULL COMMENT '总体评分: 1-5',
    time_match_rating TINYINT COMMENT '时间匹配度评分',
    course_quality_rating TINYINT COMMENT '课程质量评分',
    usability_rating TINYINT COMMENT '系统易用性评分',
    suggestion TEXT COMMENT '文字建议',
    screenshot_url VARCHAR(255) COMMENT '截图URL',
    video_url VARCHAR(255) COMMENT '录屏URL',
    is_anonymous TINYINT DEFAULT 1 COMMENT '是否匿名',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user (user_id, user_type),
    INDEX idx_semester (semester)
) COMMENT '用户反馈表';

-- 通知消息表
CREATE TABLE IF NOT EXISTS notification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '接收用户ID',
    user_type TINYINT NOT NULL COMMENT '用户类型: 1-学生, 2-教师, 3-管理员',
    title VARCHAR(100) NOT NULL COMMENT '通知标题',
    content TEXT NOT NULL COMMENT '通知内容',
    notify_type TINYINT DEFAULT 1 COMMENT '通知类型: 1-系统通知, 2-课表变更, 3-意愿提交确认',
    priority TINYINT DEFAULT 1 COMMENT '优先级: 1-普通, 2-重要, 3-紧急',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读: 0-未读, 1-已读',
    channel VARCHAR(20) DEFAULT 'SYSTEM' COMMENT '发送渠道: SYSTEM-站内, EMAIL-邮件, SMS-短信',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    read_at DATETIME COMMENT '阅读时间',
    INDEX idx_user (user_id, user_type),
    INDEX idx_unread (user_id, is_read)
) COMMENT '通知消息表';

-- ============================================
-- 6. 系统配置表
-- ============================================

-- 学期配置表
CREATE TABLE IF NOT EXISTS semester_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    semester VARCHAR(20) NOT NULL UNIQUE COMMENT '学期',
    start_date DATE NOT NULL COMMENT '学期开始日期',
    end_date DATE NOT NULL COMMENT '学期结束日期',
    preference_start DATETIME COMMENT '偏好设置开始时间',
    preference_end DATETIME COMMENT '偏好设置截止时间',
    wish_start DATETIME COMMENT '意愿提交开始时间',
    wish_end DATETIME COMMENT '意愿提交截止时间',
    schedule_publish_date DATETIME COMMENT '课表发布时间',
    is_current TINYINT DEFAULT 0 COMMENT '是否当前学期',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '学期配置表';
