package com.schedule.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("constraint_rule")
public class ConstraintRule {
    @TableId
    private String id;
    private String name;
    private String ruleType;
    private String description;
    private String parameters; // JSON
    private Boolean isActive;
}
