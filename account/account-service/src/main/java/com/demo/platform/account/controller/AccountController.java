package com.demo.platform.account.controller;

import com.demo.platform.account.api.AccountService;
import com.demo.platform.account.dto.PassportDTO;
import com.demo.platform.account.service.IUserService;
import com.demo.platform.account.dto.UserDetailDTO;
import com.demo.platform.commons.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author github.com/wanghws
 * @date 20191224
 */
@Slf4j
@Api(tags = "账号服务接口")
@RestController
public class AccountController extends BaseController implements AccountService {

    @Resource
    private IUserService userService;

    @ApiOperation("登录")
    @Override
    public UserDetailDTO login(@RequestBody @Valid PassportDTO passportDTO){
        log.info("passportDTO: {}",passportDTO);
        return userService.getUserByLoginName(passportDTO.getLoginName());
    }
}
