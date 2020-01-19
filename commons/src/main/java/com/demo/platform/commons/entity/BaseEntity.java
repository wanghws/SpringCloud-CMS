package com.demo.platform.commons.entity;

import com.baomidou.mybatisplus.annotation.*;
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
public class BaseEntity implements Serializable {

    @ApiModelProperty(value = "ID",dataType = "java.lang.String")
    @TableId(type = IdType.ASSIGN_ID)
    protected Long id;

    @ApiModelProperty(value = "通用状态 0:正常 1:停用")
    protected Status status;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除  0:正常 1:删除")
    protected Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    protected LocalDateTime updateTime;
}
