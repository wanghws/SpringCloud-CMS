package com.demo.platform.commons.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.demo.platform.commons.constants.GlobalResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 *
 * @author github.com/wanghws
 * @date 2019-02-22
 */
@Slf4j
@ApiModel("返回结果")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Response<T> implements Serializable {
    @ApiModelProperty("状态码")
    private String code;
    @ApiModelProperty("消息")
    private String message;
    @ApiModelProperty("数据")
    private T data;
    @ApiModelProperty("版本号")
    private String version;

    private Response(){}

    public static <T> Response<T> ok(){
        Response<T> response = new Response<>();
        response.setCode(GlobalResult.SUCCESS);
        return response;
    }

    public static <T> Response<T> ok(T data){
        Response<T> response = new Response<>();
        response.setCode(GlobalResult.SUCCESS);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> fail(){
        Response<T> response = new Response<>();
        response.setCode(GlobalResult.FAIL);
        return response;
    }
    public static <T> Response<T> fail(String code){
        Response<T> response = new Response<>();
        response.setCode(code);
        return response;
    }

    public static <T> Response<T> fail(String code,String message){
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}
