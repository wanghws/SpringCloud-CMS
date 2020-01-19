package com.demo.platform.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author github.com/wanghws
 * @date 20200114
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailDTO extends UserRecordDTO {

    @ApiModelProperty(value = "密码")
    private String password;

}
