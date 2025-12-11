package com.schedule.student.vo;

import lombok.Data;

/**
 * 课表视图对象
 */
@Data
public class ScheduleVO {

    /** 课程ID */
    private Long courseId;

    /** 课程名称 */
    private String courseName;

    /** 课程代码 */
    private String courseCode;

    /** 匹配状态: 1-已确定, 2-待协调, 3-冲突中, 4-未匹配 */
    private Integer matchStatus;

    /** 匹配状态描述 */
    private String matchStatusText;

    /** 匹配/未匹配原因 */
    private String reason;

    /** 时间偏好满足率 */
    private Double preferenceMatchRate;
}
