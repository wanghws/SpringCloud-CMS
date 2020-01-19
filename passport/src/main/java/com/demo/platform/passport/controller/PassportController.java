package com.demo.platform.passport.controller;

import com.google.common.base.Strings;
import com.demo.platform.commons.entity.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author github.com/wanghws
 * @date 20200108
 */
@Slf4j
@Api(tags = "登录信息接口")
@RestController
public class PassportController {

    @Resource
    private RedisTokenStore redisTokenStore;

    @ApiOperation("当前用户登录信息")
    @GetMapping("user")
    public Principal user(Principal principal) {
        return principal;
    }

    @ApiOperation("登出")
    @GetMapping("user/logout")
    public Response logout(@ApiIgnore HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (Strings.isNullOrEmpty(authorization)){
            return Response.ok();
        }
        String tokenId = authorization.substring("Bearer".length()+1);
        redisTokenStore.removeAccessToken(tokenId);
        return Response.ok();
    }
}
