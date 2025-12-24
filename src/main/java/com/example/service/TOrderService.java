package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.TOrder;
import com.example.resp.OrderInfoResp;

import java.math.BigDecimal;
import java.util.List;

/**
* @author wk794
* @description 针对表【t_order(订单表)】的数据库操作Service
* @createDate 2025-10-13 15:42:05
*/
public interface TOrderService extends IService<TOrder> {

    TOrder createOrder(String packageId);

    IPage<TOrder> getOrder(Integer pageNum, Integer pageSize,Integer  status);

    List<OrderInfoResp> getCommonOrder(Integer status);
}
