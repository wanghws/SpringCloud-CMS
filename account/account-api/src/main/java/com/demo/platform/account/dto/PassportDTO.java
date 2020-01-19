package com.demo.platform.account.dto;

import com.demo.platform.commons.constants.GlobalResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author github.com/wanghws
 * @date 20200106
 */
@Data
@ApiModel(value="PassportDTO对象", description="")
public class PassportDTO implements Serializable {
    @ApiModelProperty(value = "账号")
    @NotNull(message = GlobalResult.INVALID_NAME)
    private String loginName;
}
