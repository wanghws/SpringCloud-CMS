package com.demo.platform.passport.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author github.com/wanghws
 * @date 20200108
 */
@Slf4j
@Api(tags = "测试接口")
@Profile({"dev","test"})
@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    private RedisTokenStore redisTokenStore;

    @GetMapping("find")
    public Object find(String token){
        return redisTokenStore.readAuthentication(token);
    }

    @GetMapping("web")
    public Object web(){
        return redisTokenStore.findTokensByClientId("web");
    }

    @GetMapping("service")
    public Object service(){
        return redisTokenStore.findTokensByClientId("service");
    }


    @GetMapping("delete")
    public String delete(String client){
        Collection<OAuth2AccessToken> list = redisTokenStore.findTokensByClientId(client);
        for(OAuth2AccessToken accessToken:list){
            log.info("delete accessToken:  "+accessToken.getValue());
            redisTokenStore.removeAccessToken(accessToken.getValue());
        }
        return "success";
    }

}
