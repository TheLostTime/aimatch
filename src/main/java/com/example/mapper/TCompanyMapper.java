package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.TCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TCompanyMapper extends BaseMapper<TCompany> {
    List<TCompany> queryCompanyList(@Param("currentPage") Integer currentPage, @Param("pageSize") Integer pageSize);
    Integer queryCompanyListSize();
}
