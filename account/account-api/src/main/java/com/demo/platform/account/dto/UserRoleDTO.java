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
@ApiModel(value="User查询角色对象DTO")
public class UserRoleDTO extends BaseDTO {
    @ApiModelProperty(name = "UerId", value = "用户ID")
    @NotNull(message = GlobalResult.ACCOUNT_USER_ID_NOT_NULL)
    private Long userId;
}
