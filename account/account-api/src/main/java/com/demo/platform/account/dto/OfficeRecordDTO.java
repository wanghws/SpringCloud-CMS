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
 * 系统部门表
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Office结果对象")
public class OfficeRecordDTO extends BaseDTO {

    @ApiModelProperty(value = "weixinId")
    private String weixinId;

    @ApiModelProperty(value = "父部门ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作人的UerId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long operationId;

    @ApiModelProperty(value = "操作人名字")
    private transient String operationName;

    @ApiModelProperty(value = "父部门名字")
    private transient String parentName;

}
