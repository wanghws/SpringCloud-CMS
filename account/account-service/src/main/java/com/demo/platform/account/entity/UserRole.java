package com.demo.platform.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.demo.platform.commons.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户角色表
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="UserRole对象", description="系统用户角色表")
public class UserRole extends BaseEntity {

    private Long roleId;

    private Long userId;

    @ApiModelProperty(value = "操作人ID")
    private Long operationId;

    @ApiModelProperty(value = "角色名")
    private transient String roleName;

    @ApiModelProperty(value = "用户名")
    private transient String userName;

    @ApiModelProperty(value = "操作人名")
    private transient String operationName;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private transient Integer isDelete;
}
