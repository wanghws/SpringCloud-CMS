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
@ApiModel(value="更新对象状态DTO")
public class UpdateStatusDTO extends BaseDTO {

    @ApiModelProperty("状态 0:正常 1:停用")
    @NotNull(message = GlobalResult.ACCOUNT_STATUS_NOT_NULL)
    private Status status;
}
