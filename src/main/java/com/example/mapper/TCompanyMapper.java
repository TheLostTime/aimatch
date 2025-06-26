package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.TCompany;

import java.util.List;

public interface TCompanyMapper extends BaseMapper<TCompany> {
    List<TCompany> queryCompanyList();
}
