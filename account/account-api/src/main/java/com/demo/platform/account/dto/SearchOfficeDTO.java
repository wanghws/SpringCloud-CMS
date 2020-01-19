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
 * 系统部门表
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Office查询DTO")
public class SearchOfficeDTO extends BaseDTO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty("操作人名字")
    private String operationName;

    @ApiModelProperty("父部门名字")
    private String parentName;

    @ApiModelProperty("部门名字")
    private String officeName;

    @ApiModelProperty("状态")
    private Status status;

    @ApiModelProperty("创建时间 开始")
    private String startTime;

    @ApiModelProperty("创建时间 结束")
    private String endTime;

    @ApiModelProperty("页码")
    @Max(value = Constants.PAGE_CURRENT_MAX,message = GlobalResult.PAGE_LIMIT)
    @Min(value = Constants.PAGE_CURRENT_MIN,message = GlobalResult.PAGE_LIMIT)
    private Integer current;

    @ApiModelProperty("分页数量")
    @Max(value = Constants.PAGE_SIZE_MAX,message = GlobalResult.PAGE_LIMIT)
    @Min(value = Constants.PAGE_SIZE_MIN,message = GlobalResult.PAGE_LIMIT)
    private Integer size;

    @ApiModelProperty("查询条件:开始时间")
    private LocalDateTime startDate;

    @ApiModelProperty("查询条件:结束时间")
    private LocalDateTime endDate;
}
