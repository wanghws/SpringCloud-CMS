package com.demo.platform.account.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.demo.platform.commons.entity.BaseEntity;
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
public class Role extends BaseEntity {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "部门ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long officeId;

    @ApiModelProperty(value = "部门名字")
    private transient String officeName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作人ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long operationId;

    @ApiModelProperty(value = "操作人名")
    private transient String operationName;

    @ApiModelProperty(hidden = true)
    private transient Integer isDelete;
}
