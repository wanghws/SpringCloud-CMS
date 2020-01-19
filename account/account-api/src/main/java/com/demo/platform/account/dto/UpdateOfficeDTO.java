package com.demo.platform.account.dto;

import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.entity.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 系统部门表
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Office更新对象")
public class UpdateOfficeDTO extends BaseDTO {

    @ApiModelProperty(name = "parentId",value = "父部门ID")
    private Long parentId;
    @ApiModelProperty(name = "id",value = "部门ID(修改时传入)")
    private Long id;
    @ApiModelProperty(name = "name",value = "部门名字")
    @NotNull(message = GlobalResult.ACCOUNT_OFFICE_ID_NOT_NULL)
    private String name;
    @ApiModelProperty(name = "remark",value = "部门备注")
    private String remark;

}
