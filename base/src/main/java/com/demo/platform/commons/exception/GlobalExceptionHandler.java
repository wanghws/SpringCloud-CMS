package com.demo.platform.commons.exception;

import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 *
 * @author github.com/wanghws
 * @date 2019-02-22
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    public Response defaultNullPointerException(Exception e) {
        log.error(e.getMessage(),e);
        return Response.fail(GlobalResult.FAIL,e.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public Response defaultException(Exception e) {
        log.error(e.getMessage(),e);
        return Response.fail(GlobalResult.FAIL,e.getMessage());
    }

    @ExceptionHandler(value = ServletException.class)
    public Response missingParameterException(Exception e) {
        log.error(e.getMessage(),e);
        return Response.fail(GlobalResult.FAIL,e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        if (null!=exception.getBindingResult().getFieldError()){
            return Response.fail(exception.getBindingResult().getFieldError().getDefaultMessage(),
                    exception.getBindingResult().getFieldError().getField());
        }
        return Response.fail(GlobalResult.FAIL,exception.getMessage());
    }

    /**
     * 表单验证错误
     * */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Response constraintViolationException(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        if (!violations.isEmpty()){
            return Response.fail(violations.iterator().next().getMessage());
        }
        return Response.fail(GlobalResult.FAIL, exception.getMessage());
    }

    /**
     * 业务逻辑错误
     * */
    @ExceptionHandler(value = SysExcetpion.class)
    public Response sysException(Exception e) {
        if (e instanceof SysExcetpion){
            return Response.fail(((SysExcetpion) e).getCode());
        }
        return defaultException(e);
    }
}
