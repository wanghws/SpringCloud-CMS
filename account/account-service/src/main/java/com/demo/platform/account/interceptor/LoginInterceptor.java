package com.demo.platform.account.interceptor;

import com.alibaba.fastjson.JSON;
import com.demo.platform.account.entity.User;
import com.demo.platform.account.service.IUserService;
import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * @author github.com/wanghws
 * @date 20200109
 */
@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private IUserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Principal principal = request.getUserPrincipal();
        if (null == principal) {
            response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(Response.fail(GlobalResult.NOT_LOGIN,"principal is null!")));
            response.flushBuffer();
            return false;
        }
        User user = userService.findUserByLoginName(principal.getName());
        if (null == user){
            response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(Response.fail(GlobalResult.NOT_LOGIN,"user name not found!")));
            response.flushBuffer();
            return false;
        }
        request.setAttribute("sessionUser",user);
        return true;
    }
}
