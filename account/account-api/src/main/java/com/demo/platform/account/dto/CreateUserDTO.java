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
@ApiModel(value="User创建对象DTO")
public class CreateUserDTO extends BaseDTO {
    
    @ApiModelProperty(name = "loginName",value = "登录名")
    @NotNull(message = GlobalResult.ACCOUNT_NAME_NOT_NULL)
    private String loginName;
    
    @ApiModelProperty(name = "password",value = "密码")
    @NotNull(message = GlobalResult.ACCOUNT_PASSWORD_NOT_NULL)
    private String password;
    
    @ApiModelProperty(name = "mobile",value = "手机号")
    private String mobile;
    
    @ApiModelProperty(name = "email",value = "邮箱")
    private String email;
    
    @ApiModelProperty(name = "officeId",value = "部门ID")
    @NotNull(message = GlobalResult.ACCOUNT_OFFICE_ID_NOT_NULL)
    private Long officeId;
}
