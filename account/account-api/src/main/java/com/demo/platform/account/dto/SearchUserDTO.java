package com.demo.platform.account.dto;

import com.demo.platform.commons.constants.Constants;
import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.entity.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * @author github.com/wanghws
 * @date 20200110
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="User对象DTO")
public class SearchUserDTO extends BaseDTO {

    @ApiModelProperty(name = "officeName",value = "部门名")
    private String officeName;

    @ApiModelProperty(name = "loginName",value = "用户名")
    private String loginName;

    @ApiModelProperty(name = "mobile",value = "手机号")
    private String mobile;

    @ApiModelProperty(name = "email",value = "邮箱")
    private String email;

    @ApiModelProperty(name = "regStartTime",value = "注册开始时间")
    private String regStartTime;

    @ApiModelProperty(name = "regEndTime",value = "注册结束时间")
    private String regEndTime;

    @ApiModelProperty(name = "loginStartTime",value = "登录开始时间")
    private String loginStartTime;

    @ApiModelProperty(name = "loginEndTime",value = "登录结束时间")
    private String loginEndTime;

    @ApiModelProperty(name = "current", value = "页码")
    @Max(value = Constants.PAGE_CURRENT_MAX,message = GlobalResult.PAGE_LIMIT)
    @Min(value = Constants.PAGE_CURRENT_MIN,message = GlobalResult.PAGE_LIMIT)
    private Integer current = 1;

    @ApiModelProperty(name = "size", value = "分页数量")
    @Max(value = Constants.PAGE_SIZE_MAX,message = GlobalResult.PAGE_LIMIT)
    @Min(value = Constants.PAGE_SIZE_MIN,message = GlobalResult.PAGE_LIMIT)
    private Integer size = 10;

    @ApiModelProperty(value = "查询条件:注册开始时间")
    private transient LocalDateTime regStartDate;

    @ApiModelProperty(value = "查询条件:注册结束时间")
    private transient LocalDateTime regEndDate;

    @ApiModelProperty(value = "查询条件:登录开始时间")
    private transient LocalDateTime loginStartDate;

    @ApiModelProperty(value = "查询条件:登录结束时间")
    private transient LocalDateTime loginEndDate;

}
