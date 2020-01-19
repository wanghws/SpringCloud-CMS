package com.demo.platform.account.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.RolePermissionRecordDTO;
import com.demo.platform.account.dto.RoleRecordDTO;
import com.demo.platform.account.dto.SearchRoleDTO;
import com.demo.platform.account.dto.SearchRolePermissionDTO;
import com.demo.platform.account.entity.Role;
import com.demo.platform.account.entity.RolePermission;
import com.demo.platform.account.entity.User;
import com.demo.platform.account.mapper.RoleMapper;
import com.demo.platform.account.service.IRolePermissionService;
import com.demo.platform.account.service.IRoleService;
import com.google.common.collect.Lists;
import com.demo.platform.account.entity.Permission;
import com.demo.platform.commons.service.BaseServiceImpl;
import com.demo.platform.commons.status.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private IRolePermissionService RolePermissionService;

    public List<Role> findRoleByUser(Role role){
        return roleMapper.findRoleByUser(role);
    }
    public List<Permission> findRolePermissionsByUser(Permission permission){
        return roleMapper.findRolePermissionsByUser(permission);
    }

    public Page<RoleRecordDTO> findRolePage(Page<Role> rolePage, SearchRoleDTO searchRoleDTO) {
        return this.map(roleMapper.findRolePage(rolePage,searchRoleDTO),RoleRecordDTO.class);
    }

    public void updateRole(Role role, User user) {
        Role update = new Role();
        boolean flag = false;
        if (StringUtils.isNotBlank(role.getName())){
            update.setName(role.getName());
            flag = true;
        }
        if (StringUtils.isNotBlank(role.getRemark())) {
            update.setRemark(role.getRemark());
            flag = true;
        }
        if (role.getOfficeId() != null){
            update.setOfficeId(role.getOfficeId());
            flag = true;
        }
        if (flag){
            update.setId(role.getId());
            update.setOperationId(user.getId());
            update.setUpdateTime(LocalDateTime.now());
            updateById(update);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long roleId, Status status, User user) {
        Role update = new Role();
        update.setId(roleId);
        update.setStatus(status);
        update.setUpdateTime(LocalDateTime.now());
        update.setOperationId(user.getId());
        updateById(update);
        List<RolePermission> list = RolePermissionService.lambdaQuery().eq(RolePermission::getRoleId, roleId).list();
        if (!CollectionUtils.isEmpty(list)) {
            List<RolePermission> permissions = Lists.newArrayList();
            list.forEach(RolePermission -> {
                if (status==RolePermission.getStatus()) {
                    return;
                }
                RolePermission updatePermission = new RolePermission();
                updatePermission.setId(RolePermission.getId());
                updatePermission.setStatus(status);
                updatePermission.setUpdateTime(LocalDateTime.now());
                updatePermission.setOperationId(user.getId());
                permissions.add(updatePermission);
            });
            if (!CollectionUtils.isEmpty(permissions)){
                RolePermissionService.updateBatchById(permissions);
            }
        }
    }

    public Page<RolePermissionRecordDTO> findRolePermissionPage(Page<RolePermission> permissionPage, SearchRolePermissionDTO searchRolePermissionDTO) {
        return this.map(roleMapper.findRolePermissionPage(permissionPage,searchRolePermissionDTO),RolePermissionRecordDTO.class);
    }

    public List<RolePermission> findRolePermissionsByRole(Long roleId) {
        return roleMapper.findRolePermissionsByRole(roleId);
    }
}
