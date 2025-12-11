package com.schedule.student.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 时间段偏好明细实体类
 */
@Data
@TableName("student_time_slot")
public class TimeSlot {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 偏好ID */
    private Long preferenceId;

    /** 星期: 1-7代表周一到周日 */
    private Integer dayOfWeek;

    /** 开始时间 */
    private LocalTime startTime;

    /** 结束时间 */
    private LocalTime endTime;

    /** 偏好等级: 1-不可用, 2-不喜欢, 3-一般, 4-强烈喜欢 */
    private Integer preferenceLevel;

    /** 是否临时例外 */
    private Integer isException;

    /** 例外日期 */
    private LocalDate exceptionDate;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
