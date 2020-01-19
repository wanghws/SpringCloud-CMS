package com.demo.platform.account.entity;

import com.demo.platform.commons.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="Office对象", description="系统部门表")
public class Office extends BaseEntity {

    @ApiModelProperty(value = "weixinId")
    private String weixinId;

    @ApiModelProperty(value = "父部门ID")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作人的uerId")
    private Long operationId;

    @ApiModelProperty(value = "操作人名字")
    private transient String operationName;


    @ApiModelProperty(value = "父部门名字")
    private transient String parentName;

    @ApiModelProperty(hidden = true)
    private transient Integer isDelete;
}
