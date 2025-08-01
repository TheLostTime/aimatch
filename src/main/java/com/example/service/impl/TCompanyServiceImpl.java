package com.example.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.*;
import com.example.exception.BusinessException;
import com.example.mapper.*;
import com.example.req.*;
import com.example.resp.GetJobListResp;
import com.example.resp.HrInfoResp;
import com.example.resp.PositionDetailResp;
import com.example.service.*;
import com.example.util.FileToDbUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

import static com.example.constant.Constants.*;

@Service
@Slf4j
public class TCompanyServiceImpl extends ServiceImpl<TCompanyMapper, TCompany> implements TCompanyService{

    @Autowired
    private THrService tHrService;

    @Autowired
    private THrMapper tHrMapper;

    @Autowired
    private TCompanyMapper tCompanyMapper;

    @Autowired
    private THrVipService tHrVipService;

    @Autowired
    private THrVipMapper tHrVipMapper;

    @Autowired
    private TVipPackageService tVipPackageService;

    @Autowired
    private TVipPackageMapper tVipPackageMapper;

    @Autowired
    private THrPaidPermisionsService tHrPaidPermisionsService;

    @Autowired
    private TPositionService tPositionService;

    @Autowired
    private TPositionMapper tPositionMapper;

    @Autowired
    private TPositionKeyWordService tPositionKeyWordService;

    @Autowired
    private TPositionKeyWordMapper tPositionKeyWordMapper;

    @Autowired
    private TPositionToolboxService tPositionToolboxService;

    @Autowired
    private TPositionToolboxMapper tPositionToolboxMapper;

    @Autowired
    private THrPaidPermisionsUseDetailService tHrPaidPermisionsUseDetailService;

    @Autowired
    private TUserService tUserService;


    @Override
    public String joinCompany(HrJoinCompanyReq hrJoinCompany) {
        // 查看旗下是否有公司了
        TUser userInfo = tUserService.getById(StpUtil.getLoginId().toString());
        THr tHr = tHrService.getById(userInfo.getUserId());
        if (null!= tHr && StringUtils.isNotEmpty(tHr.getCompanyId())) {
            throw new BusinessException(10001,"您已经加入公司了");
        }

        if (null != hrJoinCompany.getAvatar()) {
            // 查询当前用户
            userInfo.setAvatar(FileToDbUtil.fileToStr(hrJoinCompany.getAvatar()));
            tUserService.saveOrUpdate(userInfo);
        }

        // 1. 公司信息录入
        TCompany tCompany = TCompany.builder()
                .companyName(hrJoinCompany.getCompanyName())
                .industry(hrJoinCompany.getIndustry())
                .scale(hrJoinCompany.getScale())
                .enterpriseCertified(COMPANY_STATUS_WAITING)
                .enterpriseLicense("")
                .incumbencyCertificate("")
                .createTime(DateUtil.date())
                .creator(hrJoinCompany.getHrName())
                .build();

        this.save(tCompany);


        // 2. hr信息录入
        THr tHr2 = THr.builder()
                .userId(userInfo.getUserId())
                .name(hrJoinCompany.getHrName())
                .title(hrJoinCompany.getTitle())
                .companyId(tCompany.getCompanyId())
                .vipType("NO")
                .realName(2)  // 未实名认证
                .build();

        tHrService.saveOrUpdate(tHr2);

        return tCompany.getCompanyId();
    }

    @Override
    public HrInfoResp getHrStatus() {
        TUser userInfo = tUserService.getById(StpUtil.getLoginId().toString());
        THr tHr = tHrService.getById(userInfo.getUserId());
        HrInfoResp hrInfo = HrInfoResp.builder()
                .userId(tHr.getUserId())
                .name(tHr.getName())
                .title(tHr.getTitle())
                .joinCompanyStatus(tHr.getCompanyId()==null?"NO":"YES")
                .realNameAuthed(tHr.getRealName()==1?"YES":"NO")
                .enterpriseCertified("NO")
                .vipType(tHr.getVipType())
                .avatar(userInfo.getAvatar())
                .build();
        TCompany tCompany = this.getById(tHr.getCompanyId());
        if (null != tCompany) {
            hrInfo.setEnterpriseCertified(tCompany.getEnterpriseCertified());
            hrInfo.setCompany(tCompany);
        }
        return hrInfo;
    }

    @Override
    public void applyCertification(MultipartFile enterpriseLicenseFile, MultipartFile incumbencyCertificateFile) {
        String enterpriseLicense = FileToDbUtil.fileToStr(enterpriseLicenseFile);
        String incumbencyCertificate = FileToDbUtil.fileToStr(incumbencyCertificateFile);
        SaSession saSession = StpUtil.getSession();
        TUser userInfo = (TUser) saSession.get("userInfo");
        THr tHr = tHrService.getById(userInfo.getUserId());
        TCompany tCompany = this.getById(tHr.getCompanyId());
        if (null != tCompany) {
            // 更新公司信息
            this.updateById(TCompany.builder()
                    .companyId(tHr.getCompanyId())
                    .incumbencyCertificate(incumbencyCertificate)
                    .enterpriseLicense(enterpriseLicense)
                    .enterpriseCertified(COMPANY_STATUS_WAITING)
                    .build());
        }
    }

    @Override
    public List<TCompany> queryCompanyList() {
        List<TCompany> list = this.getBaseMapper().queryCompanyList();
        return list;
    }

    @Override
    public void auditCompany(AuditCompanyReq auditCompanyReq) {
        // 查询公司信息
        TCompany tCompany = tCompanyMapper.selectById(auditCompanyReq.getCompanyId());
        if (!COMPANY_STATUS_WAITING.equals(tCompany.getEnterpriseCertified())) {
            throw new BusinessException(10013,"公司状态不是待审核");
        }
        if (auditCompanyReq.getStatus().equals(COMPANY_STATUS_PASS)) {
            tCompanyMapper.updateById(TCompany.builder()
                    .companyId(auditCompanyReq.getCompanyId())
                    .enterpriseCertified(COMPANY_STATUS_PASS)
                    .build());
            return;
        }
        if (auditCompanyReq.getStatus().equals(COMPANY_STATUS_FAIL)) {
            tCompanyMapper.updateById(TCompany.builder()
                    .companyId(auditCompanyReq.getCompanyId())
                    .enterpriseCertified(COMPANY_STATUS_FAIL)
                    .reason(auditCompanyReq.getReason())
                    .build());
        }
    }

    @Override
    public List<GetJobListResp> getJobList(GetJobListReq getJobListReq) {
        List<GetJobListResp> getJobListResp = tPositionMapper.getPositonInfoByReq(getJobListReq);
        return getJobListResp;
    }

    @Override
    public PositionDetailResp getPositionDetail(String positionId) {
        // 查询岗位信息
        TPosition tPosition = tPositionMapper.selectById(positionId);
        if (null == tPosition) {
            throw new BusinessException(10014,"岗位不存在");
        }

        // 查询岗位关键词
        List<TPositionKeyWord> tPositionKeyWordList = tPositionKeyWordMapper
                .selectList(new LambdaQueryWrapper<TPositionKeyWord>()
                        .eq(TPositionKeyWord::getPositionId, positionId));

        // 查询user信息
        TUser tUser = tUserService.getById(tPosition.getUserId());
        tUser.setPassword(null);

        // 查询HR信息
        THr thr = tHrService.getById(tPosition.getUserId());

        // 查询公司信息
        TCompany tCompany = tCompanyMapper.selectById(thr.getCompanyId());


        // 根据岗位ID查询工具箱信息
        TPositionToolbox tPositionToolbox = tPositionToolboxMapper.selectById(positionId);
        return PositionDetailResp.builder()
                .tPosition(tPosition)
                .tPositionToolbox(tPositionToolbox)
                .tPositionKeyWord(tPositionKeyWordList)
                .hrAvatar(tUser.getAvatar())
                .hrName(thr.getName())
                .companyName(tCompany.getCompanyName())
                .title(thr.getTitle())
                .build();
    }

    @Override
    public List<TVipPackage> getVipPackage() {
        return tVipPackageMapper.selectList(null);
    }

    @Override
    @Transactional
    public void deletePosition(String positionId) {
        // 删除岗位
        tPositionKeyWordMapper.delete(new LambdaQueryWrapper<TPositionKeyWord>()
                .eq(TPositionKeyWord::getPositionId, positionId));
        tPositionToolboxMapper.delete(new LambdaQueryWrapper<TPositionToolbox>()
                .eq(TPositionToolbox::getPositionId, positionId));
        tPositionMapper.deleteById(positionId);
    }

    @Override
    @Transactional
    public String savePosition(SavePositionReq savePositionReq) {
        String positionId = savePositionReq.getPosition().getPositionId();
        savePositionReq.getPosition().setUserId(StpUtil.getLoginId().toString());
        savePositionReq.getPosition().setUpdateTime(DateUtil.date());
        savePositionReq.getPosition().setPositionStatus(POSITION_STATUS_DRAFT);

        if (StringUtils.isNotEmpty(positionId)) {
            log.info("修改岗位...");
            savePositionReq.getPosition().setUpdateTime(DateUtil.date());
            tPositionMapper.updateById(savePositionReq.getPosition());
            if (null != savePositionReq.getKeyWords()) {
                // 删除原来的关键词
                tPositionKeyWordMapper.delete(new LambdaQueryWrapper<TPositionKeyWord>()
                        .eq(TPositionKeyWord::getPositionId, positionId));
                savePositionReq.getKeyWords().forEach(item->{
                    item.setPositionId(savePositionReq.getPosition().getPositionId());
                    tPositionKeyWordMapper.insert(item);
                });
            }
            if (null != savePositionReq.getToolbox()) {
                savePositionReq.getToolbox().setPositionId(savePositionReq.getPosition().getPositionId());
                tPositionToolboxMapper.updateById(savePositionReq.getToolbox());
            }
            return positionId;
        } else {
            log.info("保存岗位...");
            savePositionReq.getPosition().setUpdateTime(DateUtil.date());
            tPositionMapper.insert(savePositionReq.getPosition());
            if (null != savePositionReq.getKeyWords()) {
                savePositionReq.getKeyWords().forEach(item->{
                    item.setPositionId(savePositionReq.getPosition().getPositionId());
                    tPositionKeyWordMapper.insert(item);
                });
            }
            if (null != savePositionReq.getToolbox()) {
                savePositionReq.getToolbox().setPositionId(savePositionReq.getPosition().getPositionId());
                tPositionToolboxMapper.insert(savePositionReq.getToolbox());
            }
            return savePositionReq.getPosition().getPositionId();
        }
    }

    @Override
    @Transactional
    public void offlinePosition(String positionId) {
        // 查询当前岗位
        TPosition tPosition = tPositionService.getById(positionId);
        if (null == tPosition) {
            throw new BusinessException(10002,"岗位不存在");
        }
        if (tPosition.getPositionStatus() != POSITION_STATUS_ONLINE) {
            throw new BusinessException(10003,"只有上线的岗位才能下线");
        }
        tPositionMapper.updateById(TPosition.builder()
                .positionId(positionId)
                .positionStatus(POSITION_STATUS_DRAFT)
                .updateTime(DateUtil.date())
                .build());

        THrPaidPermisionsUseDetail tHrPaidPermisionsUseDetail = tHrPaidPermisionsUseDetailService
                .getById(tPosition.getUserId());
        // 岗位累计额度+1
        tHrPaidPermisionsUseDetail.setUsedPositionNum(tHrPaidPermisionsUseDetail.getUsedPositionNum()+1);
        tHrPaidPermisionsUseDetailService.updateById(tHrPaidPermisionsUseDetail);

    }



    @Override
    @Transactional
    public void publishPosition(String positionId) {
        // 查询hr信息
        THr tHr = tHrMapper.selectById(StpUtil.getLoginId().toString());
        // 查询公司信息
        TCompany tCompany = tCompanyMapper.selectById(tHr.getCompanyId());
        // 校验企业是否通过审核
        if (!tCompany.getEnterpriseCertified().equals(COMPANY_STATUS_PASS)) {
            throw new BusinessException(10017,"公司未通过审核");
        }
        // 校验是否是会员
        THrVip tHrVip = tHrVipService.getById(StpUtil.getLoginId().toString());
        if (null == tHrVip || !(VIP_HIGH.equals(tHrVip.getVipType()) || VIP_NORMAL.equals(tHrVip.getVipType()))) {
            throw new BusinessException(10004,"会员才能发布岗位");
        }
        // 查询岗位
        TPosition tPosition = tPositionService.getById(positionId);
        if (null == tPosition) {
            throw new BusinessException(10005,"岗位不存在");
        }
        if (!tPosition.getUserId().equals(StpUtil.getLoginId().toString())) {
            throw new BusinessException(10006,"您没有权限发布此岗位");
        }
        if (tPosition.getPositionStatus() != POSITION_STATUS_DRAFT) {
            throw new BusinessException(10007,"只有草稿才能发布");
        }
        // 校验付费权限使用情况
        THrPaidPermisionsUseDetail tHrPaidPermisionsUseDetail = tHrPaidPermisionsUseDetailService.getById(StpUtil.getLoginId().toString());
        if (null == tHrPaidPermisionsUseDetail) {
            throw new BusinessException(10008,"付费权限使用详情不存在");
        }

        if (tHrPaidPermisionsUseDetail.getUsedPositionNum() <= 0) {
            throw new BusinessException(10009,"已使用岗位数量已达到上限");
        }

        // 将付费权限岗位使用情况-1
        tHrPaidPermisionsUseDetail.setUsedPositionNum(tHrPaidPermisionsUseDetail.getUsedPositionNum()-1);
        tHrPaidPermisionsUseDetailService.updateById(tHrPaidPermisionsUseDetail);

        // 更新岗位状态为待审核
        tPosition.setPositionStatus(POSITION_STATUS_AUDIT);
        tPosition.setUpdateTime(DateUtil.date());
        tPositionMapper.updateById(tPosition);


    }

    @Override
    @Transactional
    public void activateVip(HrActivateReq hrActivateReq) {
        // 查询是否是会员
        THrVip tHrVipOri = tHrVipService.getById(StpUtil.getLoginId().toString());
        if (null != tHrVipOri && (tHrVipOri.getVipType().equals(VIP_NORMAL) || tHrVipOri.getVipType().equals(VIP_HIGH))) {
            throw new BusinessException(10010,"已经是会员，无需激活");
        }

        // 1.激活vip
        Date currentDate = DateUtil.date();
        Date futureDate = DateUtil.offsetDay(currentDate, hrActivateReq.getSpec());
        THrVip tHrVip = THrVip.builder()
                .vipType(hrActivateReq.getVipType())
                .userId(StpUtil.getLoginId().toString())
                .beginTime(currentDate)
                .expireTime(futureDate)
                .build();
        tHrVipService.saveOrUpdate(tHrVip);

        // 将t_hr表vip_type字段更新为hrActivateReq.getVipType()
        tHrMapper.updateById(THr.builder()
                .vipType(hrActivateReq.getVipType())
                .userId(StpUtil.getLoginId().toString())
                .build()
        );

        // 2.根据vip_type和spec查询套餐规格
        TVipPackage tVipPackage = tVipPackageMapper.queryPackage(hrActivateReq);
        if (null == tVipPackage) {
            throw new BusinessException(10011,"套餐不存在");
        }
        // 3.开通权限
        THrPaidPermisions tHrPaidPermisions = THrPaidPermisions.builder()
                .aiGenerate(tVipPackage.getAiGenerate())
                .downloadNum(tVipPackage.getDownloadNum())
                .positionNum(tVipPackage.getPositionNum())
                .sayHello(tVipPackage.getSayHello())
                .viewResume(tVipPackage.getViewResume())
                .userId(StpUtil.getLoginId().toString())
                .build();
        tHrPaidPermisionsService.saveOrUpdate(tHrPaidPermisions);

        // 4.初始化权限使用情况
        THrPaidPermisionsUseDetail tHrPaidPermisionsUseDetail = THrPaidPermisionsUseDetail.builder()
                .userId(StpUtil.getLoginId().toString())
                .usedPositionNum(tVipPackage.getPositionNum())
                .usedViewResume(tVipPackage.getViewResume())
                .usedSayHello(tVipPackage.getSayHello())
                .usedDownloadNum(tVipPackage.getDownloadNum())
                .createTime(currentDate)
                .updateTime(currentDate)
                .build();
        tHrPaidPermisionsUseDetailService.saveOrUpdate(tHrPaidPermisionsUseDetail);
    }

    @Override
    @Transactional
    public void upgradeVip(HrActivateReq hrActivateReq) {
        // 查询当期用户是否是普通vip
        THrVip tHrVip = tHrVipService.getById(StpUtil.getLoginId().toString());
        if (null == tHrVip || !tHrVip.getVipType().equals(VIP_NORMAL)) {
            throw new BusinessException(10012,"您不是普通vip");
        }

        // 升级vip
        tHrVipService.updateById(THrVip.builder()
                .userId(StpUtil.getLoginId().toString())
                .vipType(hrActivateReq.getVipType())
                .build()
        );

        // 将hr表冗余字段同步更新
        tHrMapper.updateById(THr.builder()
                .userId(StpUtil.getLoginId().toString())
                .vipType(hrActivateReq.getVipType())
                .build());

        // 2.根据vip_type和spec查询套餐规格
        TVipPackage tVipPackage = tVipPackageMapper.queryPackage(hrActivateReq);
        if (null == tVipPackage) {
            throw new BusinessException(10013,"套餐不存在");
        }

        // 3.升级套餐
        tHrPaidPermisionsService.updateById(THrPaidPermisions.builder()
                .aiGenerate(tVipPackage.getAiGenerate())
                .downloadNum(tVipPackage.getDownloadNum())
                .positionNum(tVipPackage.getPositionNum())
                .sayHello(tVipPackage.getSayHello())
                .viewResume(tVipPackage.getViewResume())
                .userId(StpUtil.getLoginId().toString())
                .build()
        );

        // 4.更新权限使用情况
        THrPaidPermisionsUseDetail tHrPaidPermisionsUseDetail = THrPaidPermisionsUseDetail.builder()
                .userId(StpUtil.getLoginId().toString())
                .usedPositionNum(tVipPackage.getPositionNum())
                .usedViewResume(tVipPackage.getViewResume())
                .usedSayHello(tVipPackage.getSayHello())
                .usedDownloadNum(tVipPackage.getDownloadNum())
                .updateTime(DateUtil.date())
                .build();
        tHrPaidPermisionsUseDetailService.updateById(tHrPaidPermisionsUseDetail);
    }


}
