package com.demo.platform.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.SearchRoleDTO;
import com.demo.platform.account.dto.SearchRolePermissionDTO;
import com.demo.platform.account.entity.Permission;
import com.demo.platform.account.entity.Role;
import com.demo.platform.account.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findRoleByUser(Role role);
    List<Permission> findRolePermissionsByUser(@Param("permission") Permission permission);

    Page<Role> findRolePage(Page<Role> rolePage, @Param("role") SearchRoleDTO searchRoleDTO);

    Page<RolePermission> findRolePermissionPage(Page<RolePermission> page, @Param("rolePermission") SearchRolePermissionDTO searchRolePermissionDTO);

    List<RolePermission> findRolePermissionsByRole(@Param("roleId") Long roleId);
}
