package com.demo.platform.passport.handler;

import com.alibaba.fastjson.JSON;
import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.entity.Response;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author github.com/wanghws
 * @date 20200108
 */
@Component
public class ClientAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(JSON.toJSONString(Response.fail(GlobalResult.NOT_PERMISSION,accessDeniedException.getMessage())));
    }
}
