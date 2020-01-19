package com.demo.platform.commons.status;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author github.com/wanghws
 * @date 19/3/22
 */

@Getter
@ToString
@AllArgsConstructor
public enum Status implements Serializable {
    NORMAL(0),//正常
    DELETE(1);//删除

    @EnumValue
    @JsonValue
    private int value;
}
