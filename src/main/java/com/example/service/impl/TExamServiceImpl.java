package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.TExam;
import com.example.entity.TResumeEducation;
import com.example.service.TExamService;
import com.example.mapper.TExamMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author wk794
* @description 针对表【t_exam】的数据库操作Service实现
* @createDate 2025-10-15 10:30:04
*/
@Service
public class TExamServiceImpl extends ServiceImpl<TExamMapper, TExam>
    implements TExamService{


    @Override
    public List<TExam> getExamInfo(String positionId, String resumeId) {
        // 查询岗位的考试信息
        List<TExam> examInfoList = this.baseMapper.selectList(new LambdaQueryWrapper<TExam>()
                .eq(TExam::getResumeId, resumeId).eq(TExam::getPositionId, positionId))
                .stream().sorted(Comparator.comparingInt(TExam::getQueIndex)).collect(Collectors.toList());
        return examInfoList;
    }
}




