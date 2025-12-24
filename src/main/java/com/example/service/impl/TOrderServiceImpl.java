package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constant.PayConstant;
import com.example.entity.TOrder;
import com.example.entity.TPayment;
import com.example.entity.TVipPackage;
import com.example.exception.BusinessException;
import com.example.mapper.TOrderMapper;
import com.example.mapper.TPaymentMapper;
import com.example.mapper.TVipPackageMapper;
import com.example.resp.OrderInfoResp;
import com.example.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
* @author wk794
* @description 针对表【t_order(订单表)】的数据库操作Service实现
* @createDate 2025-10-13 15:42:05
*/
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder>
    implements TOrderService{

    @Autowired
    private TVipPackageMapper tVipPackageMapper;

    @Autowired
    private TPaymentMapper tPaymentMapper;
    @Override
    public TOrder createOrder(String packageId) {
        // 查询套餐
        TVipPackage tVipPackage = tVipPackageMapper.selectById(packageId);
        if (null == tVipPackage) {
            throw new BusinessException(10011,"套餐不存在");
        }
        String userId = StpUtil.getLoginId().toString();
        TOrder order = new TOrder();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setTotalAmount(tVipPackage.getPrice());
        order.setStatus(PayConstant.ORDER_STATUS_PENDING); // 待支付
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        order.setPackageId(packageId);
        order.setPackageName(tVipPackage.getPackageName());
        baseMapper.insert(order);
        return order;
    }

    @Override
    public IPage<TOrder> getOrder(Integer pageNum, Integer pageSize,Integer status) {
        IPage<TOrder> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(TOrder::getCreateTime);
        queryWrapper.eq(status != null, TOrder::getStatus, status);
        IPage<TOrder> orderList = this.baseMapper.selectPage(page, queryWrapper);
        // 获取所有id
        List<String> ids = orderList.getRecords().stream().map(TOrder::getId).collect(Collectors.toList());
        List<TPayment> paymentList = tPaymentMapper.selectList(new LambdaQueryWrapper<TPayment>().in(TPayment::getOrderId, ids));
        orderList.getRecords().forEach(order -> {
            TPayment payment = paymentList.stream()
                    .filter(p -> p.getOrderId().equals(order.getId())).findFirst().orElse(null);
            if (payment != null) {
                order.setChannel(payment.getChannel());
            }
        });
        return orderList;
    }

    @Override
    public List<OrderInfoResp> getCommonOrder(Integer status) {
        String userId = StpUtil.getLoginId().toString();
        List<OrderInfoResp> orderList = this.baseMapper.queryOrderIncDetail(userId,status);
        return orderList;
    }

    private String generateOrderNo() {
        return "ORDER" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}




