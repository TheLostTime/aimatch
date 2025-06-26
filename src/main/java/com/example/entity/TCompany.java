package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="TCompany")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "t_company")
public class TCompany implements Serializable {
    /**
     * 公司id
     */
    @TableId(value = "company_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="公司id")
    private String companyId;

    /**
     * 公司名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="公司名称")
    private String name;

    /**
     * 所属行业
     */
    @TableField(value = "industry")
    @ApiModelProperty(value="所属行业")
    private String industry;

    /**
     * 公司规模1:0-20,2:20-99,3:100-499
     */
    @TableField(value = "scale")
    @ApiModelProperty(value="公司规模1:0-20,2:20-99,3:100-499")
    private String scale;

    /**
     * 企业认证标志:YES-已认证NO-未认证WAITING-等待审核FAIL-审核失败
     */
    @TableField(value = "enterprise_certified")
    @ApiModelProperty(value="企业认证标志:YES-已认证NO-未认证WAITING-等待审核FAIL-审核失败PASS-审核通过")
    private String enterpriseCertified;

    /**
     * 营业执照(二进制文件base64字符串)
     */
    @TableField(value = "enterprise_license")
    @ApiModelProperty(value="营业执照(二进制文件base64字符串)")
    private String enterpriseLicense;

    /**
     * 在职证明(二进制文件base64字符串)
     */
    @TableField(value = "incumbency_certificate")
    @ApiModelProperty(value="在职证明(二进制文件base64字符串)")
    private String incumbencyCertificate;

    @TableField(value = "reason")
    @ApiModelProperty(value="审核未通过理由")
    private String reason;

    private static final long serialVersionUID = 1L;
}