package com.demo.platform.account.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.demo.platform.commons.entity.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 系统权限表
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Permission结果对象")
public class PermissionRecordDTO extends BaseDTO {

    @ApiModelProperty(value = "父权限ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long parentId;

    @ApiModelProperty(value = "父权限名字")
    private String parentName;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "权限关键字")
    private String permission;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "链接")
    private String url;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否显示 0显示1隐藏")
    private Integer hidden;

    @ApiModelProperty(value = "操作人ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long operationId;

    private List<PermissionRecordDTO> permissionList;

    private Long userId;

    @ApiModelProperty(value = "操作人名字")
    private String operationName;



}
