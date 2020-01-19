package com.demo.platform.account.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.PermissionRecordDTO;
import com.demo.platform.account.dto.SearchPermissionDTO;
import com.demo.platform.account.dto.UpdatePermissionDTO;
import com.demo.platform.account.dto.UpdateStatusDTO;
import com.demo.platform.account.entity.Permission;
import com.demo.platform.account.entity.RolePermission;
import com.demo.platform.account.entity.User;
import com.demo.platform.account.entity.UserRole;
import com.demo.platform.account.service.IPermissionService;
import com.demo.platform.account.service.IRolePermissionService;
import com.demo.platform.account.service.IUserRoleService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统权限表 前端控制器
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Api(tags = "权限管理接口")
@Slf4j
@Validated
@RestController
@RequestMapping("permission")
public class PermissionController extends BaseController {

    @Resource
    private IPermissionService permissionService;

    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IRolePermissionService rolePermissionService;

    @Secured("ROLE_PERMISSION_LIST")
    @ApiOperation(value = "权限列表")
    @PostMapping("list")
    public Response<Page<PermissionRecordDTO>> list(@RequestBody @Valid SearchPermissionDTO searchPermissionDTO){

        if (StringUtils.isNotBlank(searchPermissionDTO.getStartTime())){
            searchPermissionDTO.setStartDate(DateUtil.getDateParse(searchPermissionDTO.getStartTime()));
        }
        if (StringUtils.isNotBlank(searchPermissionDTO.getEndTime())){
            searchPermissionDTO.setEndDate(DateUtil.getDateParse(searchPermissionDTO.getEndTime()));
        }

        return Response.ok(permissionService.findPermissionPage(new Page<>(searchPermissionDTO.getCurrent(), searchPermissionDTO.getSize()),searchPermissionDTO));
    }

    @Secured("ROLE_PERMISSION_SAVE")
    @ApiOperation(value = "创建/修改权限信息")
    @PostMapping("save")
    public Response<PermissionRecordDTO> save(@RequestBody @Valid UpdatePermissionDTO updatePermissionDTO,
            @ApiIgnore User sessionUser){
        if (updatePermissionDTO.getParentId() == null
                && StringUtils.isBlank(updatePermissionDTO.getName())
                && StringUtils.isBlank(updatePermissionDTO.getPermission())
                && StringUtils.isBlank(updatePermissionDTO.getUrl())
                && StringUtils.isBlank(updatePermissionDTO.getRemark())
                && updatePermissionDTO.getHidden() == null
                && updatePermissionDTO.getSort() == null) {
            return Response.ok();
        }
        Permission permission;
        if (StringUtils.isNotBlank(updatePermissionDTO.getPermission())){
            Permission one = permissionService.lambdaQuery()
                    .eq(Permission::getStatus, Status.NORMAL)
                    .eq(Permission::getPermission, updatePermissionDTO.getPermission())
                    .one();
            if (one != null
                    && updatePermissionDTO.getId() != null
                    && !one.getId().equals(updatePermissionDTO.getId())){
                return Response.fail(GlobalResult.ACCOUNT_PERMISSION_REPEAT);
            }
        }
        if (StringUtils.isNotBlank(updatePermissionDTO.getUrl())){
            Permission one = permissionService.lambdaQuery()
                    .eq(Permission::getStatus, Status.NORMAL)
                    .eq(Permission::getUrl, updatePermissionDTO.getUrl())
                    .one();
            if (one != null
                    && updatePermissionDTO.getId() != null
                    && !one.getId().equals(updatePermissionDTO.getId())){
                return Response.fail(GlobalResult.ACCOUNT_URL_REPEAT);
            }
        }
        if (updatePermissionDTO.getId() != null) {
            permission = permissionService.getById(updatePermissionDTO.getId());
            if (permission == null || Status.NORMAL != permission.getStatus()) {
                return Response.fail(GlobalResult.ACCOUNT_PERMISSION_RECORD_NOT_FOUND);
            }
            if (updatePermissionDTO.getParentId() != null && updatePermissionDTO.getParentId() != 0) {
                permission.setParentId(updatePermissionDTO.getParentId());
            }
            if (StringUtils.isNotBlank(updatePermissionDTO.getName())) {
                permission.setName(updatePermissionDTO.getName());
            }
            if (StringUtils.isNotBlank(updatePermissionDTO.getPermission())) {
                permission.setPermission(updatePermissionDTO.getPermission());
            }
            if (StringUtils.isNotBlank(updatePermissionDTO.getRemark())) {
                permission.setRemark(updatePermissionDTO.getRemark());
            }
            if (StringUtils.isNotBlank(updatePermissionDTO.getUrl())) {
                permission.setUrl(updatePermissionDTO.getUrl());
            }
            if (updatePermissionDTO.getHidden() != null) {
                permission.setHidden(updatePermissionDTO.getHidden());
            }
            if (updatePermissionDTO.getSort() != null) {
                permission.setSort(updatePermissionDTO.getSort());
            }
            permission.setUpdateTime(LocalDateTime.now());
            permission.setOperationId(sessionUser.getId());
            permissionService.updateById(permission);
        } else {
            permission = new Permission();
            if (StringUtils.isBlank(updatePermissionDTO.getName())) {
                return Response.fail(GlobalResult.ACCOUNT_NAME_NOT_NULL);
            }
            if (StringUtils.isBlank(updatePermissionDTO.getPermission())) {
                return Response.fail(GlobalResult.ACCOUNT_PERMISSION_NOT_NULL);
            }
            if (StringUtils.isBlank(updatePermissionDTO.getUrl())) {
                return Response.fail(GlobalResult.ACCOUNT_URL_NOT_NULL);
            }
            permission = permissionService.map(updatePermissionDTO,Permission.class);

            permission.setStatus(Status.NORMAL);
            permission.setOperationId(sessionUser.getId());
            permissionService.save(permission);
        }
        PermissionRecordDTO permissionRecordDTO = permissionService.map(permission,PermissionRecordDTO.class);
        Long permissionParentId = permission.getParentId();
        if (permissionParentId != null && permissionParentId != 0){
            Permission parent = permissionService.getById(permissionParentId);
            permissionRecordDTO.setParentName(parent != null ? parent.getName() : "");
        }
        permissionRecordDTO.setOperationName(sessionUser.getLoginName());
        return Response.ok(permissionRecordDTO);
    }

    @Secured("ROLE_PERMISSION_STATUS")
    @ApiOperation(value = "权限状态修改")
    @PostMapping("status")
    public Response<?> status(@RequestBody @Valid UpdateStatusDTO updateStatusDTO, @ApiIgnore User sessionUser){
        Permission permission = permissionService.getById(updateStatusDTO.getId());
        if (permission == null) {
            return Response.fail(GlobalResult.ACCOUNT_PERMISSION_RECORD_NOT_FOUND);
        }
        if (updateStatusDTO.getStatus() == permission.getStatus()) {
            return Response.fail(GlobalResult.ACCOUNT_STATUS_INVALID);
        }
        permissionService.updateStatus(updateStatusDTO.getId(),updateStatusDTO.getStatus(),sessionUser);
        return Response.ok();
    }

    @Secured("ROLE_PERMISSION_ALL")
    @ApiOperation(value = "查询全部权限")
    @PostMapping( "all")
    public Response<List<PermissionRecordDTO>> all(){
        List<Permission> list = permissionService.lambdaQuery().eq(Permission::getStatus, Status.NORMAL).list();
        return Response.ok(permissionService.map(list,PermissionRecordDTO.class));
    }

    @ApiOperation(value = "权限树形菜单")
    @PostMapping("tree")
    public Response<List<Map<String,Object>>> tree(@ApiIgnore User sessionUser){
        //todo
        sessionUser.setIsAdmin(true);

        List<Permission> rootList;
        if (sessionUser.getIsAdmin()){
            rootList =  permissionService.lambdaQuery()
                    .isNull(Permission::getParentId)
                    .eq(Permission::getStatus, Status.NORMAL)
                    .list();
        }else{
            List<UserRole> userRoleList = userRoleService.lambdaQuery()
                    .eq(UserRole::getUserId,sessionUser.getId())
                    .eq(UserRole::getStatus,Status.NORMAL)
                    .list();
            if (CollectionUtils.isEmpty(userRoleList)){
                return Response.ok();
            }
            List<Long> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            List<RolePermission> rolePermissionList = rolePermissionService.lambdaQuery()
                    .in(RolePermission::getRoleId,roleIdList)
                    .eq(RolePermission::getStatus,Status.NORMAL)
                    .list();
            List<Long> permissionList = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
            rootList = permissionService.lambdaQuery()
                    .isNull(Permission::getParentId)
                    .in(Permission::getId,permissionList)
                    .eq(Permission::getStatus,Status.NORMAL)
                    .list();

        }
        Map<String,Object> rootMap;
        List<Map<String,Object>> treeList = new ArrayList<>();
        for(Permission permission:rootList){
            rootMap = new HashMap<>();
            rootMap.put("id",permission.getId()+"");
            rootMap.put("label",permission.getName());
            rootMap.put("children",permissionService.treeList(permission));
            treeList.add(rootMap);
        }

        return Response.ok(treeList);
    }
}

