package com.demo.platform.passport.handler;

import com.alibaba.fastjson.JSON;
import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.entity.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author github.com/wanghws
 * @date 20200108
 */
@Component
public class ClientAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException {
        try{
            JSON.writeJSONString(response.getOutputStream(),Response.fail(GlobalResult.NOT_PERMISSION,authException.getMessage()));
        }catch (Exception e){
            throw new ServletException(e.getMessage());
        }
    }
}
