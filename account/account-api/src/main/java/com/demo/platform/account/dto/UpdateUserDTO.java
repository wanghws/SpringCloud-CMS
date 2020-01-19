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
@ApiModel(value="User更新对象DTO")
public class UpdateUserDTO extends BaseDTO {
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("部门ID")
    @NotNull(message = GlobalResult.ACCOUNT_OFFICE_ID_NOT_NULL)
    private Long officeId;
}
