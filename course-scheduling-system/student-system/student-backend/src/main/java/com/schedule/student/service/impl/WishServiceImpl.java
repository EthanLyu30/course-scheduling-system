package com.schedule.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.student.dto.WishDTO;
import com.schedule.student.entity.Course;
import com.schedule.student.entity.Wish;
import com.schedule.student.mapper.CourseMapper;
import com.schedule.student.mapper.WishMapper;
import com.schedule.student.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 意愿表达服务实现类
 */
@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {

    private final WishMapper wishMapper;
    private final CourseMapper courseMapper;

    @Override
    public List<Wish> getWishList(Long studentId, String semester) {
        LambdaQueryWrapper<Wish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Wish::getStudentId, studentId)
               .eq(Wish::getSemester, semester)
               .orderByDesc(Wish::getPriority)
               .orderByDesc(Wish::getCreatedAt);
        
        List<Wish> wishes = wishMapper.selectList(wrapper);
        
        // 填充课程信息
        for (Wish wish : wishes) {
            Course course = courseMapper.selectCourseDetail(wish.getCourseId());
            wish.setCourse(course);
        }
        
        return wishes;
    }

    @Override
    @Transactional
    public Wish addWish(Long studentId, WishDTO wishDTO) {
        // 检查是否已存在该课程的意愿
        LambdaQueryWrapper<Wish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Wish::getStudentId, studentId)
               .eq(Wish::getCourseId, wishDTO.getCourseId())
               .eq(Wish::getSemester, wishDTO.getSemester());
        
        if (wishMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("该课程已在意愿列表中");
        }

        Wish wish = new Wish();
        wish.setStudentId(studentId);
        wish.setCourseId(wishDTO.getCourseId());
        wish.setSemester(wishDTO.getSemester());
        wish.setPriority(wishDTO.getPriority());
        wish.setReason(wishDTO.getReason());
        wish.setStatus(0); // 待提交
        
        wishMapper.insert(wish);
        return wish;
    }

    @Override
    public Wish updateWish(Long wishId, WishDTO wishDTO) {
        Wish wish = wishMapper.selectById(wishId);
        if (wish == null) {
            throw new RuntimeException("意愿不存在");
        }
        
        wish.setPriority(wishDTO.getPriority());
        wish.setReason(wishDTO.getReason());
        wishMapper.updateById(wish);
        
        return wish;
    }

    @Override
    public void deleteWish(Long wishId) {
        wishMapper.deleteById(wishId);
    }

    @Override
    @Transactional
    public void submitWishes(Long studentId, String semester) {
        LambdaQueryWrapper<Wish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Wish::getStudentId, studentId)
               .eq(Wish::getSemester, semester)
               .eq(Wish::getStatus, 0);
        
        List<Wish> wishes = wishMapper.selectList(wrapper);
        
        for (Wish wish : wishes) {
            wish.setStatus(1); // 已提交
            wish.setSubmittedAt(LocalDateTime.now());
            wishMapper.updateById(wish);
        }
    }

    @Override
    public List<String> detectConflicts(Long studentId, String semester) {
        List<String> conflicts = new ArrayList<>();
        List<Wish> wishes = getWishList(studentId, semester);
        
        // 计算总学分
        double totalCredits = 0;
        for (Wish wish : wishes) {
            if (wish.getCourse() != null) {
                totalCredits += wish.getCourse().getCredits().doubleValue();
            }
        }
        
        // 学分上限检测（假设上限25学分）
        if (totalCredits > 25) {
            conflicts.add(String.format("总学分%.1f超过上限25学分", totalCredits));
        }
        
        // TODO: 时间冲突检测
        // TODO: 先修课检测
        
        return conflicts;
    }
}
