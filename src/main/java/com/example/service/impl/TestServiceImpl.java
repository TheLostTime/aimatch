package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Test;
import com.example.service.TestService;
import com.example.mapper.TestMapper;
import org.springframework.stereotype.Service;

/**
* @author wk794
* @description 针对表【test】的数据库操作Service实现
* @createDate 2025-06-06 13:52:58
*/
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test>
    implements TestService{

}




