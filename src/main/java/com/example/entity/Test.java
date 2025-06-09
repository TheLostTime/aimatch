package com.example.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName test
*/
public class Test implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Long id;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("")
    @Length(max= 50,message="编码长度不能超过50")
    private String username;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("")
    @Length(max= 100,message="编码长度不能超过100")
    private String password;
    /**
    * 
    */
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("")
    @Length(max= 50,message="编码长度不能超过50")
    private String email;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("")
    @Length(max= 20,message="编码长度不能超过20")
    private String userType;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer enabled;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Date createTime;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Date updateTime;
    /**
    * 
    */
    @ApiModelProperty("")
    private Date lastLoginTime;

    /**
    * 主键
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 
    */
    private void setUsername(String username){
    this.username = username;
    }

    /**
    * 
    */
    private void setPassword(String password){
    this.password = password;
    }

    /**
    * 
    */
    private void setEmail(String email){
    this.email = email;
    }

    /**
    * 
    */
    private void setUserType(String userType){
    this.userType = userType;
    }

    /**
    * 
    */
    private void setEnabled(Integer enabled){
    this.enabled = enabled;
    }

    /**
    * 
    */
    private void setCreateTime(Date createTime){
    this.createTime = createTime;
    }

    /**
    * 
    */
    private void setUpdateTime(Date updateTime){
    this.updateTime = updateTime;
    }

    /**
    * 
    */
    private void setLastLoginTime(Date lastLoginTime){
    this.lastLoginTime = lastLoginTime;
    }


    /**
    * 主键
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 
    */
    private String getUsername(){
    return this.username;
    }

    /**
    * 
    */
    private String getPassword(){
    return this.password;
    }

    /**
    * 
    */
    private String getEmail(){
    return this.email;
    }

    /**
    * 
    */
    private String getUserType(){
    return this.userType;
    }

    /**
    * 
    */
    private Integer getEnabled(){
    return this.enabled;
    }

    /**
    * 
    */
    private Date getCreateTime(){
    return this.createTime;
    }

    /**
    * 
    */
    private Date getUpdateTime(){
    return this.updateTime;
    }

    /**
    * 
    */
    private Date getLastLoginTime(){
    return this.lastLoginTime;
    }

}
