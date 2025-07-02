package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.example.entity.THr;
import com.example.entity.THrVip;
import com.example.exception.BusinessException;
import com.example.mapper.THrMapper;
import com.example.req.ResumeListReq;
import com.example.resp.ResumeItem;
import com.example.resp.ResumeListResp;
import com.example.service.THrVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.TPositionMapper;
import com.example.entity.TPosition;
import com.example.service.TPositionService;

import static com.example.constant.Constants.*;

@Service
public class TPositionServiceImpl extends ServiceImpl<TPositionMapper, TPosition> implements TPositionService{

    @Autowired
    private THrMapper tHrMapper;

    @Autowired
    private THrVipService tHrVipService;


    @Override
    public List<TPosition> queryPositionList() {
        // 查询所有岗位
        return baseMapper.selectList( null);
    }

    @Override
    public void auditPosition(String positionId, Integer status, String reason) {
        // 查询当前岗位
        TPosition tPosition = baseMapper.selectById(positionId);
        if (null == tPosition) {
            throw new BusinessException(10015,"岗位不存在");
        }
        if (tPosition.getPositionStatus() != POSITION_STATUS_AUDIT) {
            throw new BusinessException(10017,"只有待审核的岗位才能审核");
        }
        if (status == POSITION_STATUS_ONLINE) {
            // 审核通过
            baseMapper.updateById(TPosition.builder()
                    .positionId(positionId)
                    .positionStatus(POSITION_STATUS_ONLINE)
                    .updateTime(DateUtil.date())
                    .build());
        }
        if (status == POSITION_STATUS_REJECT) {
            // 审核不通过
            baseMapper.updateById(TPosition.builder()
                    .positionId(positionId)
                    .positionStatus(POSITION_STATUS_REJECT)
                    .updateTime(DateUtil.date())
                    .reason(reason)
                    .build());
        }
    }

    @Override
    public ResumeListResp getResumeList(ResumeListReq resumeListReq) {
        THrVip tHrVip = tHrVipService.getById(StpUtil.getLoginId().toString());
        if (resumeListReq.getQueryType().equals("ai")
                && (null == tHrVip || !tHrVip.getVipType().equals(VIP_NORMAL)
                        || !tHrVip.getVipType().equals(VIP_HIGH) )) {
                throw new BusinessException(10020,"会员才能使用ai智能筛选");
        }
        return null;
    }
}
