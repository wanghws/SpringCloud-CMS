package com.demo.platform.account.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.demo.platform.commons.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统角色权限表
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="RolePermission对象", description="系统角色权限表")
public class RolePermission extends BaseEntity {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long roleId;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long permissionId;

    @ApiModelProperty(value = "操作人ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long operationId;

    @ApiModelProperty(value = "操作人名")
    private transient String operationName;

    @ApiModelProperty(hidden = true)
    private transient Integer isDelete;
}
