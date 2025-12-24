package com.example.service;

import com.example.entity.TExam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wk794
* @description 针对表【t_exam】的数据库操作Service
* @createDate 2025-10-15 10:30:04
*/
public interface TExamService extends IService<TExam> {

    List<TExam> getExamInfo(String positionId, String resumeId);
}
