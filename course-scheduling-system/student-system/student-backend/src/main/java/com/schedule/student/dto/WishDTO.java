package com.schedule.student.dto;

import lombok.Data;

/**
 * 意愿DTO
 */
@Data
public class WishDTO {

    /** 课程ID */
    private Long courseId;

    /** 学期 */
    private String semester;

    /** 优先级: 1-一般想选, 2-强烈想选, 3-必选 */
    private Integer priority;

    /** 选课理由 */
    private String reason;
}
