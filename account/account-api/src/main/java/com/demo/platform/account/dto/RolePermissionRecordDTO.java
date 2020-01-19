package com.demo.platform.account.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.demo.platform.commons.entity.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author github.com/wanghws
 * @date 20200113
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="RolePermission结果对象")
public class RolePermissionRecordDTO extends BaseDTO {

    @ApiModelProperty(value = "角色ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long roleId;

    @ApiModelProperty(value = "权限ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long permissionId;

    @ApiModelProperty(value = "操作人ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long operationId;


    @ApiModelProperty(value = "操作人名字")
    private transient String operationName;

    @ApiModelProperty(value = "角色名字")
    private transient String roleName;

    @ApiModelProperty(value = "权限名字")
    private transient String permissionName;

}
