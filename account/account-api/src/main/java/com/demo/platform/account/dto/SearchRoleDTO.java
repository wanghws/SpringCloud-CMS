package com.demo.platform.account.dto;

import com.demo.platform.commons.constants.Constants;
import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.entity.BaseDTO;
import com.demo.platform.commons.status.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

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
public class SearchRoleDTO extends BaseDTO {

    @ApiModelProperty(name = "operationName",value = "操作人名字")
    private String operationName;

    @ApiModelProperty(name = "name",value = "角色名")
    private String name;

    @ApiModelProperty(name = "officeName",value = "部门名")
    private String officeName;

    @ApiModelProperty(name = "status",value = "状态 1正常，2删除")
    private Status status;

    @ApiModelProperty(name = "startTime",value = "创建时间 开始")
    private String startTime;

    @ApiModelProperty(name = "endTime",value = "创建时间 结束")
    private String endTime;

    @ApiModelProperty(name = "current", value = "页码")
    @Max(value = Constants.PAGE_CURRENT_MAX,message = GlobalResult.PAGE_LIMIT)
    @Min(value = Constants.PAGE_CURRENT_MIN,message = GlobalResult.PAGE_LIMIT)
    private Integer current;

    @ApiModelProperty(name = "size", value = "分页数量")
    @Max(value = Constants.PAGE_SIZE_MAX,message = GlobalResult.PAGE_LIMIT)
    @Min(value = Constants.PAGE_SIZE_MIN,message = GlobalResult.PAGE_LIMIT)
    private Integer size;

    @ApiModelProperty(value = "查询条件:开始时间")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "查询条件:结束时间")
    private LocalDateTime endDate;

}
