package com.example.req;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel(value="HrActivateReq")
@Data
@AllArgsConstructor
public class HrActivateReq {

    @ApiModelProperty(value="VIP类型，0-NO-非vip 1-NORMAL-普通vip 2-HIGH-豪华vip 3-EXPIRED-已过期")
    private String vipType;

    @ApiModelProperty(value="30 60 90天")
    private Integer spec;

}
