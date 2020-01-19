package com.demo.platform.account.dto;

import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.entity.BaseDTO;
import com.demo.platform.commons.status.Status;
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
@ApiModel(value="更新用户角色状态DTO")
public class UpdateUserRoleStatusDTO extends BaseDTO {

    @ApiModelProperty("用户角色关系ID")
    @NotNull(message = GlobalResult.ACCOUNT_USER_ROLE_ID_NOT_NULL)
    private Long userRoleId;

    @ApiModelProperty("状态 0正常 1禁用")
    @NotNull(message = GlobalResult.ACCOUNT_STATUS_NOT_NULL)
    private Status status;
}
