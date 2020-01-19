package com.demo.platform.account.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.*;
import com.demo.platform.account.entity.Office;
import com.demo.platform.account.entity.Role;
import com.demo.platform.account.entity.User;
import com.demo.platform.account.entity.UserRole;
import com.demo.platform.account.service.IOfficeService;
import com.demo.platform.account.service.IRoleService;
import com.demo.platform.account.service.IUserRoleService;
import com.demo.platform.account.service.IUserService;
import com.google.common.collect.Lists;
import com.demo.platform.account.dto.*;
import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.constants.ValidationPattern;
import com.demo.platform.commons.controller.BaseController;
import com.demo.platform.commons.entity.Response;
import com.demo.platform.commons.status.Status;
import com.demo.platform.commons.utils.DateUtil;
import com.demo.platform.commons.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 管理后台用户
 * </p>
 *
 * @author yanwenkai
 * @since 2019-03-08
 */
@Api(tags = "用户管理接口")
@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private IUserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private IOfficeService officeService;

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IRoleService roleService;

    @ApiOperation("用户详情")
    @GetMapping("info")
    public Response<UserRecordDTO> info(@ApiIgnore User sessionUser) {

        return Response.ok(userService.map(sessionUser,UserRecordDTO.class));
    }

    @Secured("ROLE_USER_LIST")
    @ApiOperation(value = "用户列表")
    @PostMapping("list")
    public Response<Page<UserRecordDTO>> list(@RequestBody @Valid SearchUserDTO searchUserDTO){
        if (StringUtils.isNotBlank(searchUserDTO.getRegStartTime())){
            searchUserDTO.setRegStartDate(DateUtil.getDateParse(searchUserDTO.getRegStartTime()));
        }
        if (StringUtils.isNotBlank(searchUserDTO.getRegEndTime())){
            searchUserDTO.setRegEndDate(DateUtil.getDateParse(searchUserDTO.getRegEndTime()));
        }
        if (StringUtils.isNotBlank(searchUserDTO.getLoginStartTime())){
            searchUserDTO.setLoginStartDate(DateUtil.getDateParse(searchUserDTO.getLoginStartTime()));
        }
        if (StringUtils.isNotBlank(searchUserDTO.getLoginEndTime())){
            searchUserDTO.setLoginEndDate(DateUtil.getDateParse(searchUserDTO.getLoginEndTime()));
        }
        return Response.ok(userService.findUserPage(new Page<>(searchUserDTO.getCurrent(),searchUserDTO.getSize()),searchUserDTO));
    }

    @Secured("ROLE_USER_CREATE")
    @ApiOperation(value = "创建用户")
    @PostMapping("create")
    public Response<UserRecordDTO> create(@RequestBody @Valid CreateUserDTO createUserDTO, HttpServletRequest request){
        User userByName = userService.lambdaQuery().eq(User::getLoginName, createUserDTO.getLoginName()).one();
        if (userByName != null) {
            return Response.fail(GlobalResult.ACCOUNT_NAME_NOT_REPEAT);
        }
        if (StringUtils.isNotBlank(createUserDTO.getMobile())){
            if (!WebUtils.checkPatten(createUserDTO.getMobile(), ValidationPattern.MOBILE_PATTERN)){
                return Response.fail(GlobalResult.INVALID_MOBILE);
            }
            User userByMobile = userService.lambdaQuery().eq(User::getMobile, createUserDTO.getMobile()).one();
            if (userByMobile != null){
                return Response.fail(GlobalResult.ACCOUNT_MOBILE_NOT_REPEAT);
            }
        }
        if (StringUtils.isNotBlank(createUserDTO.getEmail())){
            if (!WebUtils.checkPatten(createUserDTO.getEmail(),ValidationPattern.EMAIL_PATTERN)){
                return Response.fail(GlobalResult.INVALID_EMAIL);
            }
            User userByEmail = userService.lambdaQuery().eq(User::getEmail, createUserDTO.getEmail()).one();
            if (userByEmail != null){
                return Response.fail(GlobalResult.ACCOUNT_EMAIL_NOT_REPEAT);
            }
        }
        Office office = officeService.getById(createUserDTO.getOfficeId());
        if (office == null){
            return Response.fail(GlobalResult.ACCOUNT_OFFICE_NOT_FOUND);
        }
        if (Status.DELETE == office.getStatus()){
            return Response.fail(GlobalResult.ACCOUNT_OFFICE_STATUS_INVALID);
        }
        User user = new User();
        user.setLoginName(createUserDTO.getLoginName());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setEmail(createUserDTO.getEmail());
        user.setMobile(createUserDTO.getMobile());
        user.setOfficeId(createUserDTO.getOfficeId());
        user.setRegisterIp(WebUtils.getClientIp(request));
        user.setRegisterTime(LocalDateTime.now());
        user.setStatus(Status.NORMAL);
        user.setOfficeName(office.getName());
        userService.save(user);
        return Response.ok(userService.map(user,UserRecordDTO.class));
    }

    @Secured("ROLE_USER_UPDATE")
    @ApiOperation(value = "用户修改")
    @PostMapping("update")
    public Response<UserRecordDTO> update(@RequestBody @Valid UpdateUserDTO updateUserDTO){
        if (StringUtils.isBlank(updateUserDTO.getMobile())
                && StringUtils.isBlank(updateUserDTO.getEmail())
                && updateUserDTO.getOfficeId() == null){
            return Response.ok();
        }
        User user = userService.getById(updateUserDTO.getId());
        if (user == null || user.getStatus() != Status.NORMAL) {
            return Response.fail(GlobalResult.ACCOUNT_USER_NOT_FOUND);
        }
        User update = new User();
        if (StringUtils.isNotBlank(updateUserDTO.getMobile())){
            if (!WebUtils.checkPatten(updateUserDTO.getMobile(),ValidationPattern.MOBILE_PATTERN)){
                return Response.fail(GlobalResult.INVALID_MOBILE);
            }
            User userByMobile = userService.lambdaQuery().eq(User::getMobile, updateUserDTO.getMobile()).one();
            if (userByMobile != null && !updateUserDTO.getMobile().equals(userByMobile.getMobile())){
                return Response.fail(GlobalResult.ACCOUNT_MOBILE_NOT_REPEAT);
            }
            update.setMobile(updateUserDTO.getMobile());
            user.setMobile(updateUserDTO.getMobile());
        }
        if (StringUtils.isNotBlank(updateUserDTO.getEmail())){
            if (!WebUtils.checkPatten(updateUserDTO.getEmail(),ValidationPattern.EMAIL_PATTERN)){
                return Response.fail(GlobalResult.INVALID_EMAIL);
            }
            User userByEmail = userService.lambdaQuery().eq(User::getEmail, updateUserDTO.getEmail()).one();
            if (userByEmail != null && !updateUserDTO.getEmail().equals(userByEmail.getEmail())){
                return Response.fail(GlobalResult.ACCOUNT_EMAIL_NOT_REPEAT);
            }
            update.setEmail(updateUserDTO.getEmail());
            user.setEmail(updateUserDTO.getEmail());
        }
        if (updateUserDTO.getOfficeId() != null) {
            Office office = officeService.getById(updateUserDTO.getOfficeId());
            if (office == null || Status.NORMAL != office.getStatus()){
                return Response.fail(GlobalResult.ACCOUNT_OFFICE_NOT_FOUND);
            }
            update.setOfficeId(updateUserDTO.getOfficeId());
            user.setOfficeId(updateUserDTO.getOfficeId());
            user.setOfficeName(office.getName());
        }
        update.setId(updateUserDTO.getId());
        userService.updateById(update);
        return Response.ok(userService.map(user,UserRecordDTO.class));
    }

    @Secured("ROLE_USER_STATUS")
    @ApiOperation(value = "修改用户状态")
    @PostMapping("status")
    public Response<?> status(@RequestBody @Valid UpdateStatusDTO updateStatusDTO){
        User user = userService.getById(updateStatusDTO.getId());
        if (user == null){
            return Response.fail(GlobalResult.ACCOUNT_USER_NOT_FOUND);
        }
        if (user.getStatus() == updateStatusDTO.getStatus()){
            return Response.fail(GlobalResult.ACCOUNT_STATUS_INVALID);
        }
        User update= new User();
        update.setStatus(updateStatusDTO.getStatus());
        update.setId(user.getId());
        userService.updateById(update);
        return Response.ok();
    }

    @Secured("ROLE_USER_ROLE_SAVE")
    @ApiOperation(value = "用户角色关系保存")
    @PostMapping("role/save")
    public Response<?> saveRole(@RequestBody @Valid UpdateUserRoleDTO updateUserRoleDTO, @ApiIgnore User sessionUser) {
        List<UserRole> list = userRoleService.lambdaQuery()
                .eq(UserRole::getUserId, updateUserRoleDTO.getId())
                .eq(UserRole::getStatus, Status.NORMAL)
                .list();
        List<UserRole> oldUserRoles = Lists.newArrayList();
        list.forEach(UserRole -> {
            UserRole.setStatus(Status.DELETE);
            oldUserRoles.add(UserRole);
        });
        if (!CollectionUtils.isEmpty(oldUserRoles)){
            userRoleService.updateBatchById(oldUserRoles);
        }
        User userById = userService.getById(updateUserRoleDTO.getId());
        if (userById == null) {
            return Response.fail(GlobalResult.ACCOUNT_USER_NOT_FOUND);
        }
        if (userById.getStatus() != Status.NORMAL) {
            return Response.fail(GlobalResult.ACCOUNT_USER_STATUS_INVALID);
        }
        long roleId;
        List<UserRole> roleList = Lists.newArrayList();
        for (String s : updateUserRoleDTO.getRoleIds()) {
            try {
                roleId = Long.parseLong(s);
            } catch (NumberFormatException e) {
                log.error(e.getMessage(), e);
                return Response.fail(GlobalResult.ACCOUNT_ROLE_ID_NOT_NULL);
            }
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(updateUserRoleDTO.getId());
            userRole.setStatus(Status.NORMAL);
            userRole.setOperationId(sessionUser.getId());
            roleList.add(userRole);
        }
        User roleUser = userService.getById(updateUserRoleDTO.getId());
        userRoleService.createBatch(roleUser,roleList);
        return Response.ok();
    }

    @Secured("ROLE_USER_ROLES")
    @ApiOperation(value = "用户对应所有角色")
    @PostMapping("roles")
    public Response<List<UserRoleRecordDTO>> userRoleByUser(@RequestBody @Valid UserRoleDTO userRoleDTO){
        User userById = userService.getById(userRoleDTO.getUserId());
        if (userById == null){
            return Response.fail(GlobalResult.ACCOUNT_USER_NOT_FOUND);
        }
        if (userById.getStatus() != Status.NORMAL){
            return Response.fail(GlobalResult.ACCOUNT_USER_STATUS_INVALID);
        }
        List<UserRole> list = userService.userRoleByUser(userRoleDTO.getUserId());
        return Response.ok(userService.map(list,UserRoleRecordDTO.class));
    }

    @Secured("ROLE_USER_ROLE_STATUS")
    @ApiOperation(value = "用户角色关系状态修改")
    @PostMapping("role/status")
    public Response<?> roleStatus(@RequestBody @Valid  UpdateUserRoleStatusDTO updateUserRoleStatusDTO,
            @ApiIgnore User User){
        UserRole userRoleById = userRoleService.getById(updateUserRoleStatusDTO.getUserRoleId());
        if (userRoleById == null){
            return Response.fail(GlobalResult.ACCOUNT_USER_ROLE_NOT_FOUND);
        }
        if (userRoleById.getStatus() == updateUserRoleStatusDTO.getStatus()){
            return Response.fail(GlobalResult.ACCOUNT_STATUS_INVALID);
        }
        userRoleService.updateStatus(updateUserRoleStatusDTO.getUserRoleId(),updateUserRoleStatusDTO.getStatus(),User);
        return Response.ok();
    }

    @ApiOperation(value = "获取当前用户角色ID集合")
    @PostMapping("current/roles")
    public Response myRoles(@RequestBody @Valid UserRoleDTO userRoleDTO){
        List<UserRole> list = userRoleService.lambdaQuery()
                .eq(UserRole::getUserId, userRoleDTO.getUserId())
                .eq(UserRole::getStatus, Status.NORMAL)
                .list();
        if (CollectionUtils.isEmpty(list)){
            return Response.ok();
        }
        Set<String> roles = new HashSet<>();
        for(UserRole userRole:list){
            Role role = roleService.getById(userRole.getRoleId());
            if (null == role){
                continue;
            }
            roles.add(role.getId()+"");
        }

        return Response.ok(roles);
    }
}

