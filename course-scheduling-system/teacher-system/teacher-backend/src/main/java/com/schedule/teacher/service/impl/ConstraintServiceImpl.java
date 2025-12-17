package com.schedule.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.teacher.entity.ConstraintRule;
import com.schedule.teacher.mapper.ConstraintRuleMapper;
import com.schedule.teacher.service.ConstraintService;
import com.schedule.teacher.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstraintServiceImpl implements ConstraintService {

    private final ConstraintRuleMapper constraintRuleMapper;

    @Override
    public Result<List<ConstraintRule>> listActive() {
        return Result.ok(constraintRuleMapper.selectList(new LambdaQueryWrapper<ConstraintRule>()
                .eq(ConstraintRule::getIsActive, true)));
    }
}
