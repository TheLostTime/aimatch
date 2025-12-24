package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.TOrder;
import com.example.resp.OrderInfoResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk794
 * @description 针对表【t_order(订单表)】的数据库操作Mapper
 * @createDate 2025-10-13 15:42:05
 * @Entity com.example.entity.TOrder
 */
public interface TOrderMapper extends BaseMapper<TOrder> {

    List<OrderInfoResp> queryOrderIncDetail(@Param("userId") String userId, @Param("status") Integer status);
}




