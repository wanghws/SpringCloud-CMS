package com.demo.platform.account.service;

import com.demo.platform.account.entity.RolePermission;
import com.demo.platform.account.entity.User;
import com.demo.platform.commons.service.IBaseService;
import com.demo.platform.commons.status.Status;

/**
 * <p>
 * 系统角色权限表 服务类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
public interface IRolePermissionService extends IBaseService<RolePermission> {

    void updateStatus(Long rolePermissionId, Status status, User user);
}
