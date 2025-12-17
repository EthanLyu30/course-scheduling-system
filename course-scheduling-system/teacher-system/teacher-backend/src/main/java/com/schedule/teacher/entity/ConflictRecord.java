package com.schedule.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("conflict_record")
public class ConflictRecord {
    @TableId
    private String id;
    private String planId;
    private String assignmentId;
    private String conflictType;
    private Integer severityLevel;
    private String rootCause;
    private String resolutionStatus;
    private LocalDateTime resolvedAt;
}
