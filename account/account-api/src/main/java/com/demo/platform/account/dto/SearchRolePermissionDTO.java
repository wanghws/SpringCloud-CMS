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
 * @author github.com/wanghws
 * @date 20200113
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="RolePermission结果对象")
public class SearchRolePermissionDTO extends BaseDTO {
    @ApiModelProperty(name = "roleName",value = "角色名")
    private String roleName;

    @ApiModelProperty(name = "permissionName",value = "权限名字")
    private String permissionName;

    @ApiModelProperty(name = "operationName",value = "操作人名字")
    private String operationName;

    @ApiModelProperty(name = "status",value = "状态 0:删除 1:正常")
    private Status status;

    @ApiModelProperty(name = "startTime",value = "时间查询开始")
    private String startTime;

    @ApiModelProperty(name = "endTime",value = "时间查询结束")
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
    private transient LocalDateTime startDate;

    @ApiModelProperty(value = "查询条件:结束时间")
    private transient LocalDateTime endDate;

}
