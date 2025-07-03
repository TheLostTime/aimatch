package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.TEmployee;
import com.example.mapper.TEmployeeMapper;
import com.example.service.TEmployeeService;
import org.springframework.stereotype.Service;
@Service
public class TEmployeeServiceImpl extends ServiceImpl<TEmployeeMapper, TEmployee> implements TEmployeeService{

}
