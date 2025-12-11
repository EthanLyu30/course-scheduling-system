package com.schedule.student.service;

import com.schedule.student.dto.WishDTO;
import com.schedule.student.entity.Wish;
import java.util.List;

/**
 * 意愿表达服务接口
 */
public interface WishService {

    /**
     * 获取学生意愿列表
     */
    List<Wish> getWishList(Long studentId, String semester);

    /**
     * 添加课程意愿
     */
    Wish addWish(Long studentId, WishDTO wishDTO);

    /**
     * 更新意愿优先级
     */
    Wish updateWish(Long wishId, WishDTO wishDTO);

    /**
     * 删除意愿
     */
    void deleteWish(Long wishId);

    /**
     * 提交意愿
     */
    void submitWishes(Long studentId, String semester);

    /**
     * 检测意愿冲突（时间、学分、先修课）
     */
    List<String> detectConflicts(Long studentId, String semester);
}
