package com.demo.platform.account.entity;

import com.demo.platform.commons.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="User对象", description="系统用户表")
public class User extends BaseEntity {

    @ApiModelProperty(value = "weixinId")
    private String weixinId;

    @ApiModelProperty(value = "部门ID")
    private Long officeId;

    @ApiModelProperty(value = "账号")
    private String loginName;


    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "密码时间")
    private LocalDateTime passwordTime;

    @ApiModelProperty(value = "密码ip")
    private String passwordIp;

    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginTime;

    @ApiModelProperty(value = "登录IP")
    private String loginIp;

    @ApiModelProperty(value = "注册时间")
    private LocalDateTime registerTime;

    @ApiModelProperty(value = "注册IP")
    private String registerIp;

    @ApiModelProperty(value = "部门名字")
    private transient String officeName;

    @ApiModelProperty(hidden = true)
    private transient LocalDateTime createTime;

    @ApiModelProperty(hidden = true)
    private transient Integer isDelete;

    @ApiModelProperty("是否是系统管理员")
    private transient Boolean isAdmin = true;
}
