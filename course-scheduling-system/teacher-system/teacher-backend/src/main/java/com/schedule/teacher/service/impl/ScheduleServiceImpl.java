package com.schedule.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.schedule.teacher.entity.CourseAssignment;
import com.schedule.teacher.mapper.CourseAssignmentMapper;
import com.schedule.teacher.service.ScheduleService;
import com.schedule.teacher.vo.Result;
import com.schedule.teacher.vo.ScheduleVO;
import com.schedule.teacher.event.DomainEventPublisher;
import com.schedule.teacher.event.ScheduleConfirmedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final CourseAssignmentMapper assignmentMapper;
    private final DomainEventPublisher eventPublisher;

    @Override
    public Result<List<ScheduleVO>> getSchedule(String teacherId) {
        List<CourseAssignment> list = assignmentMapper.selectList(new LambdaQueryWrapper<CourseAssignment>()
                .eq(CourseAssignment::getTeacherId, teacherId));
        List<ScheduleVO> vos = list.stream().map(a -> {
            ScheduleVO vo = new ScheduleVO();
            vo.setPlanId(a.getPlanId());
            vo.setCourseId(String.valueOf(a.getCourseId()));
            vo.setTeacherId(a.getTeacherId());
            vo.setRoomId(a.getRoomId());
            vo.setStatus(a.getStatus());
            return vo;
        }).collect(Collectors.toList());
        return Result.ok(vos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> confirm(String planId) {
        // 占位：事务确认，可补充状态检查与教务协同 MQ 通知
        assignmentMapper.update(null, new LambdaUpdateWrapper<CourseAssignment>()
            .eq(CourseAssignment::getPlanId, planId)
            .set(CourseAssignment::getStatus, "CONFIRMED"));
        eventPublisher.publishScheduleConfirmed(new ScheduleConfirmedEvent(planId, ""));
        return Result.ok();
    }
}
