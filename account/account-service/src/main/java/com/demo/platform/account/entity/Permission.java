package com.demo.platform.account.entity;

import com.demo.platform.commons.entity.BaseEntity;
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
@ApiModel(value="Permission对象", description="系统权限表")
public class Permission extends BaseEntity {

    @ApiModelProperty(value = "父权限ID")
    private Long parentId;

    @ApiModelProperty(value = "父权限名字")
    private transient String parentName;

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
    private Long operationId;

    @ApiModelProperty(value = "操作人名")
    private transient String operationName;

    @ApiModelProperty(hidden = true)
    private transient Integer isDelete;

    @ApiModelProperty(hidden = true)
    private transient Long userId;
}
