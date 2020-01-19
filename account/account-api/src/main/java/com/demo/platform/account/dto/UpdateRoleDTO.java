package com.demo.platform.account.dto;

import com.demo.platform.commons.entity.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Role对象", description="系统角色表")
public class UpdateRoleDTO extends BaseDTO {
    @ApiModelProperty(name = "name",value = "角色名")
    private String name;
    
    @ApiModelProperty(name = "officeId",value = "部门ID")
    private Long officeId;
    
    @ApiModelProperty(name = "remark",value = "备注")
    private String remark;

}
