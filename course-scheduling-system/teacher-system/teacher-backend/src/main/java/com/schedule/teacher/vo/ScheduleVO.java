package com.schedule.teacher.vo;

import lombok.Data;

@Data
public class ScheduleVO {
    private String planId;
    private String courseId;
    private String courseName;
    private String teacherId;
    private String teacherName;
    private String roomId;
    private String roomName;
    private Integer dayOfWeek;
    private Integer slotNumber;
    private String status;
}
