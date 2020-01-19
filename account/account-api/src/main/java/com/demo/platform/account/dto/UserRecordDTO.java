package com.demo.platform.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.demo.platform.commons.entity.BaseDTO;
import com.demo.platform.commons.status.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author github.com/wanghws
 * @date 20200110
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="User结果对象DTO")
public class UserRecordDTO extends BaseDTO {

    @ApiModelProperty(name = "officeName",value = "部门名")
    private String officeName;

    @ApiModelProperty(name = "loginName",value = "用户名")
    private String loginName;

    @ApiModelProperty(name = "mobile",value = "手机号")
    private String mobile;

    @ApiModelProperty(name = "email",value = "邮箱")
    private String email;

    @ApiModelProperty(name = "status",value = "状态")
    private Status status;

    @ApiModelProperty(value = "部门ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long officeId;

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "登录IP")
    private String loginIp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "密码时间")
    private LocalDateTime passwordTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "注册时间")
    private LocalDateTime registerTime;

    @ApiModelProperty(value = "权限路由")
    private  Set<String> roles;

    @ApiModelProperty(value = "操作人名字")
    private String operationName;
}
