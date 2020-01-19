package com.demo.platform.commons.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.demo.platform.commons.status.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author github.com/wanghws
 * @date 2019-02-21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDTO implements Serializable {

    @JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty(value = "ID",dataType = "java.lang.String")
    private Long id;

    @ApiModelProperty(value = "通用状态 0:正常 1:停用")
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
}
