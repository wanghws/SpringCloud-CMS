package com.demo.platform.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.RolePermissionRecordDTO;
import com.demo.platform.account.dto.RoleRecordDTO;
import com.demo.platform.account.dto.SearchRoleDTO;
import com.demo.platform.account.dto.SearchRolePermissionDTO;
import com.demo.platform.account.entity.Permission;
import com.demo.platform.account.entity.Role;
import com.demo.platform.account.entity.RolePermission;
import com.demo.platform.account.entity.User;
import com.demo.platform.commons.service.IBaseService;
import com.demo.platform.commons.status.Status;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
public interface IRoleService extends IBaseService<Role> {
    List<Role> findRoleByUser(Role Role);
    List<Permission> findRolePermissionsByUser(Permission permission);

    Page<RoleRecordDTO> findRolePage(Page<Role> rolePage, SearchRoleDTO searchRoleDTO);
    void updateRole(Role update, User user);
    void updateStatus(Long roleId, Status status, User user);
    Page<RolePermissionRecordDTO> findRolePermissionPage(Page<RolePermission> permissionPage, SearchRolePermissionDTO searchRolePermissionDTO);

    List<RolePermission> findRolePermissionsByRole(Long roleId);
}
