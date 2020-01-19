package com.demo.platform.account.controller;

import com.demo.platform.account.entity.User;
import com.demo.platform.account.service.IUserService;
import com.demo.platform.commons.constants.Constants;
import com.demo.platform.commons.controller.BaseController;
import com.demo.platform.commons.entity.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author github.com/wanghws
 * @date 20200114
 */
@Profile({"dev","test"})
@Api(tags = "测试接口")
@Slf4j
@RestController
@RequestMapping("test")
public class TestController extends BaseController {
    @Resource
    private IUserService userService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "重置admin密码")
    @GetMapping("user")
    public Response<User> test(){

        User user = new User();
        user.setId(1L);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setPasswordTime(LocalDateTime.now());
        userService.updateById(user);

        return Response.ok(user);
    }

    @Resource
    private RedisCacheManager redisCacheManager;
    @ApiOperation(value = "删除缓存")
    @GetMapping("cache/delete")
    public Response deleteCache(){
        redisCacheManager.getCache(Constants.CACHE_ACCOUNT).clear();
        return Response.ok();
    }
}
