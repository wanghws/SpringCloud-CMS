package com.demo.platform.account.dto;

import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.entity.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author github.com/wanghws
 * @date 20200110
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="查询角色权限对象DTO")
public class RolePermissionDTO extends BaseDTO {
    @ApiModelProperty(name = "roleId", value = "角色ID")
    @NotNull(message = GlobalResult.ACCOUNT_ROLE_ID_NOT_NULL)
    private Long roleId;
}
