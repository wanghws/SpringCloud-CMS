package com.demo.platform.account.interceptor;

import com.demo.platform.account.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by github.com/wanghws on 2019-02-27.
 */
public class UserArgumentResolver implements WebArgumentResolver {
    @Override
    public Object resolveArgument(MethodParameter methodParameter,NativeWebRequest nativeWebRequest) throws Exception {
        if(! methodParameter.getParameterType().equals(User.class)) {
            return UNRESOLVED;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        return httpServletRequest.getAttribute("sessionUser");
    }
}