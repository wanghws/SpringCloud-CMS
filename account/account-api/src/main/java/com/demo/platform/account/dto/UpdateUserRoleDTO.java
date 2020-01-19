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
@ApiModel(value="User更新角色状态DTO")
public class UpdateUserRoleDTO extends BaseDTO {
    @ApiModelProperty(name = "roleIds", value = "角色ID，逗号分隔")
    @NotNull(message = GlobalResult.ACCOUNT_ROLE_ID_NOT_NULL)
    private String[] roleIds;
}
