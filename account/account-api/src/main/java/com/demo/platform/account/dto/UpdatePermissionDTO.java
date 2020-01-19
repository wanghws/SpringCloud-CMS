package com.demo.platform.account.dto;

import com.demo.platform.commons.entity.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="Permission查询对象")
public class UpdatePermissionDTO extends BaseDTO {

    @ApiModelProperty(name = "parentId",value = "父权限ID")
    private Long parentId;
    
    @ApiModelProperty(name = "name",value = "权限名字")
    private String name;
    
    @ApiModelProperty(name = "permission",value = "权限关键字")
    private String permission;
    
    @ApiModelProperty(name = "url",value = "url")
    private String url;
    
    @ApiModelProperty(name = "remark",value = "备注")
    private String remark;
    
    @ApiModelProperty(name = "hidden",value = "是否显示 0显示 1隐藏")
    private Integer hidden;
    
    @ApiModelProperty(name = "sort",value = "排序")
    private Integer sort;
}
