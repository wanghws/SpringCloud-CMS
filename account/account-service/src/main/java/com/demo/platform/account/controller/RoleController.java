package com.demo.platform.account.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.*;
import com.demo.platform.account.entity.*;
import com.demo.platform.account.service.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.demo.platform.account.dto.*;
import com.demo.platform.account.entity.*;
import com.demo.platform.account.service.*;
import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.controller.BaseController;
import com.demo.platform.commons.entity.Response;
import com.demo.platform.commons.status.Status;
import com.demo.platform.commons.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Api(tags = "角色管理接口")
@Slf4j
@Validated
@RestController
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private IRoleService roleService;

    @Resource
    private IRolePermissionService rolePermissionService;

    @Resource
    private IOfficeService officeService;

    @Resource
    private IPermissionService permissionService;

    @Resource
    private IUserRoleService userRoleService;

    @Secured("ROLE_LIST")
    @ApiOperation(value = "角色列表")
    @PostMapping("list")
    public Response<Page<RoleRecordDTO>> list(@RequestBody @Valid SearchRoleDTO searchRoleDTO){

        if (StringUtils.isNotBlank(searchRoleDTO.getStartTime())){
            searchRoleDTO.setStartDate(DateUtil.getDateParse(searchRoleDTO.getStartTime()));
        }
        if (StringUtils.isNotBlank(searchRoleDTO.getEndTime())){
            searchRoleDTO.setEndDate(DateUtil.getDateParse(searchRoleDTO.getEndTime()));
        }
        return Response.ok(roleService.findRolePage(new Page<>(searchRoleDTO.getCurrent(),searchRoleDTO.getSize()),searchRoleDTO));
    }

    @Secured("ROLE_SAVE")
    @ApiOperation(value = "创建/修改角色信息")
    @PostMapping("save")
    public Response<RoleRecordDTO> save(@RequestBody @Valid UpdateRoleDTO updateRoleDTO,
                               @ApiIgnore User sessionUser){
        if (StringUtils.isBlank(updateRoleDTO.getName())
                && updateRoleDTO.getOfficeId() == null
                && !StringUtils.isNotBlank(updateRoleDTO.getRemark())){
            return Response.fail();
        }
        Role role;
        if (updateRoleDTO.getId() != null) {
            role = roleService.getById(updateRoleDTO.getId());
            if (role == null) {
                return Response.fail(GlobalResult.ACCOUNT_ROLE_NOT_FOUND);
            }
            if (role.getStatus() == Status.DELETE){
                return Response.fail(GlobalResult.ACCOUNT_STATUS_INVALID);
            }
            if (StringUtils.isNotBlank(updateRoleDTO.getName())){
                role.setName(updateRoleDTO.getName());
            }
            if (updateRoleDTO.getOfficeId() != null && updateRoleDTO.getOfficeId() != 0) {
                role.setOfficeId(updateRoleDTO.getOfficeId());
            }
            if (StringUtils.isNotBlank(updateRoleDTO.getRemark())){
                role.setRemark(updateRoleDTO.getRemark());
            }
            Role update = new Role();
            update.setId(updateRoleDTO.getId());
            update.setName(updateRoleDTO.getName());
            update.setOfficeId(updateRoleDTO.getOfficeId());
            update.setRemark(updateRoleDTO.getRemark());
            roleService.updateRole(update,sessionUser);
        } else {
            role = new Role();
            if (StringUtils.isBlank(updateRoleDTO.getName())){
                return Response.fail(GlobalResult.ACCOUNT_NAME_NOT_NULL);
            }
            if (updateRoleDTO.getOfficeId() == null){
                return Response.fail(GlobalResult.ACCOUNT_OFFICE_ID_NOT_NULL);
            }
            role.setName(updateRoleDTO.getName());
            role.setRemark(updateRoleDTO.getRemark());
            role.setOperationId(sessionUser.getId());
            role.setOfficeId(updateRoleDTO.getOfficeId());
            role.setCreateTime(LocalDateTime.now());
            role.setStatus(Status.NORMAL);
            role.setUpdateTime(LocalDateTime.now());
            roleService.save(role);
        }
        RoleRecordDTO roleRecordDTO = roleService.map(role,RoleRecordDTO.class);

        if (updateRoleDTO.getOfficeId() != null && updateRoleDTO.getOfficeId() != 0){
            Office office = officeService.getById(updateRoleDTO.getOfficeId());
            roleRecordDTO.setOfficeName(office != null ? office.getName() : "");
        }
        roleRecordDTO.setOperationName(sessionUser.getLoginName());
        return Response.ok(roleRecordDTO);
    }

    @Secured("ROLE_STATUS")
    @ApiOperation(value = "角色状态修改")
    @PostMapping("status")
    public Response<?> status(@RequestBody @Valid UpdateStatusDTO updateStatusDTO,
                              @ApiIgnore User sessionUser){
        Role role = roleService.getById(updateStatusDTO.getId());
        if (role == null) {
            return Response.fail(GlobalResult.ACCOUNT_ROLE_NOT_FOUND);
        }
        if (role.getStatus() == updateStatusDTO.getStatus()) {
            return Response.fail(GlobalResult.ACCOUNT_STATUS_INVALID);
        }
        roleService.updateStatus(updateStatusDTO.getId(),updateStatusDTO.getStatus(),sessionUser);
        return Response.ok();
    }

    @Secured("ROLE_PERMISSION_SAVE")
    @ApiOperation(value = "角色权限关系保存")
    @PostMapping(value = "permission/save")
    public Response<?> permissionSave(@RequestBody @Valid UpdateUserPermissionDTO userPermissionDTO,
                                      @ApiIgnore User sessionUser){
        List<RolePermission> list = rolePermissionService.lambdaQuery()
                .eq(RolePermission::getRoleId, userPermissionDTO.getId())
                .list();
        List<RolePermission> oldPermissionList = Lists.newArrayList();
        list.forEach(rolePermission -> {
            rolePermission.setStatus(Status.DELETE);
            oldPermissionList.add(rolePermission);
        });
        if (!CollectionUtils.isEmpty(oldPermissionList)){
            rolePermissionService.updateBatchById(oldPermissionList);
        }
        List<RolePermission> permissionList = Lists.newArrayList();
        for (int i = 0; i < userPermissionDTO.getPermissionIds().length; i++) {
            Long permissionId;
            try {
                permissionId = Long.valueOf(userPermissionDTO.getPermissionIds()[i]);
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
                return Response.fail(GlobalResult.ACCOUNT_PERMISSION_ID_NOT_NULL);
            }
            List<Permission> permissions;
            permissions = permissionService.lambdaQuery().eq(Permission::getParentId, permissionId).list();
            if (!CollectionUtils.isEmpty(permissions)){
                permissions.forEach(permission -> {
                    if (permission.getId().equals(permissionId)) {
                        return;
                    }
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(userPermissionDTO.getId());
                    rolePermission.setPermissionId(permission.getId());
                    rolePermission.setOperationId(sessionUser.getId());
                    rolePermission.setStatus(Status.NORMAL);
                    permissionList.add(rolePermission);
                });
            }
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(userPermissionDTO.getId());
            rolePermission.setPermissionId(permissionId);
            rolePermission.setOperationId(sessionUser.getId());
            rolePermission.setCreateTime(LocalDateTime.now());
            rolePermission.setUpdateTime(LocalDateTime.now());
            rolePermission.setStatus(Status.NORMAL);
            permissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(permissionList);
        return Response.ok();
    }

    @Secured("ROLE_PERMISSION_LIST")
    @ApiOperation(value = "角色权限关系列表")
    @PostMapping("permission/list")
    public Response<Page<RolePermissionRecordDTO>> permissionList(@RequestBody @Valid SearchRolePermissionDTO searchRolePermissionDTO){

        if (StringUtils.isNotBlank(searchRolePermissionDTO.getStartTime())){
            searchRolePermissionDTO.setStartDate(DateUtil.getDateParse(searchRolePermissionDTO.getStartTime()));
        }
        if (StringUtils.isNotBlank(searchRolePermissionDTO.getEndTime())){
            searchRolePermissionDTO.setEndDate(DateUtil.getDateParse(searchRolePermissionDTO.getEndTime()));
        }
        return Response.ok(roleService.findRolePermissionPage(new Page<>(searchRolePermissionDTO.getCurrent(),searchRolePermissionDTO.getSize()),searchRolePermissionDTO));
    }

    @Secured("ROLE_PERMISSIONS_ROLE")
    @ApiOperation(value = "角色对应所有权限")
    @PostMapping("permissions/role")
    public Response<List<RolePermissionRecordDTO>> permissionsByRole(@RequestBody @Valid RolePermissionDTO rolePermissionDTO){
        List<RolePermission> list = roleService.findRolePermissionsByRole(rolePermissionDTO.getRoleId());
        return Response.ok(roleService.map(list,RolePermissionRecordDTO.class));
    }

    @Secured("ROLE_PERMISSION_STATUS")
    @ApiOperation(value = "角色权限关系状态修改")
    @PostMapping("permission/status")
    public Response<?> permissionStatus(@RequestBody @Valid UpdateStatusDTO updateStatusDTO,
            @ApiIgnore User sessionUser){
        RolePermission RolePermission = rolePermissionService.getById(updateStatusDTO.getId());
        if (RolePermission == null) {
            return Response.fail(GlobalResult.ACCOUNT_ROLE_PERMISSION_NOT_FOUND);
        }
        if (RolePermission.getStatus() == updateStatusDTO.getStatus()){
            return Response.fail(GlobalResult.ACCOUNT_STATUS_INVALID);
        }
        rolePermissionService.updateStatus(updateStatusDTO.getId(),updateStatusDTO.getStatus(),sessionUser);
        return Response.ok();
    }

    @ApiOperation(value = "获取角色所有权限")
    @PostMapping("current/permissions")
    public Response<Set<String>> currentPermissions(@RequestBody @Valid RolePermissionDTO rolePermissionDTO){
        List<RolePermission> list = rolePermissionService.lambdaQuery()
                .eq(RolePermission::getRoleId, rolePermissionDTO.getRoleId())
                .eq(RolePermission::getStatus, Status.NORMAL)
                .list();
        if (CollectionUtils.isEmpty(list)){
            return Response.ok(Sets.newHashSet());
        }
        Set<String> set = Sets.newHashSet();
        for (RolePermission permission : list) {
            set.add(permission.getPermissionId().toString());
        }
        return Response.ok(set);
    }
    @ApiOperation(value = "角色树型菜单")
    @PostMapping("tree")
    public Response<List<Map<String,Object>>> tree(@ApiIgnore User sessionUser){
        List<Role> roleList;
        //todo
        sessionUser.setIsAdmin(true);

        if (sessionUser.getIsAdmin()){
            roleList = roleService.lambdaQuery()
                    .eq(Role::getStatus, Status.NORMAL)
                    .list();
        }else{
            List<UserRole> list = userRoleService.lambdaQuery()
                    .eq(UserRole::getUserId, sessionUser.getId())
                    .eq(UserRole::getStatus, Status.NORMAL)
                    .list();
            if (CollectionUtils.isEmpty(list)){
                return Response.ok();
            }
            List<Long> roleIdList = list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            roleList = roleService.lambdaQuery()
                    .in(Role::getId,roleIdList)
                    .eq(Role::getStatus, Status.NORMAL)
                    .list();
        }

        List<Map<String,Object>> treeList = Lists.newArrayList();
        Map<String,Object> row;
        for(Role role:roleList){
            row = Maps.newHashMap();
            row.put("id",role.getId()+"");
            row.put("label",role.getName());
            treeList.add(row);
        }
        return Response.ok(treeList);
    }
}

