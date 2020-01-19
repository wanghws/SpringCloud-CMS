package com.demo.platform.account.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class RoleRecordDTO extends BaseDTO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "部门ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long officeId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作人ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long operationId;

    private Long userId;

    @ApiModelProperty(value = "操作人名字")
    private String operationName;

    @ApiModelProperty(value = "部门名字")
    private String officeName;

}
