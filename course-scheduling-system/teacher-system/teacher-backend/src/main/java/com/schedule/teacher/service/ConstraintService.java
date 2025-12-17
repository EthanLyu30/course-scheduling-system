package com.schedule.teacher.service;

import com.schedule.teacher.entity.ConstraintRule;
import com.schedule.teacher.vo.Result;

import java.util.List;

public interface ConstraintService {
    Result<List<ConstraintRule>> listActive();
}
