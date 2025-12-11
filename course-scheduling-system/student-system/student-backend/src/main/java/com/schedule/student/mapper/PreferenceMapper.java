package com.schedule.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.schedule.student.entity.TimePreference;
import org.apache.ibatis.annotations.Mapper;

/**
 * 时间偏好Mapper接口
 */
@Mapper
public interface PreferenceMapper extends BaseMapper<TimePreference> {

}
