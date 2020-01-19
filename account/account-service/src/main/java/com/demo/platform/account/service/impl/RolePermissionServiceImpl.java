package com.demo.platform.account.service.impl;

import com.demo.platform.account.entity.RolePermission;
import com.demo.platform.account.entity.User;
import com.demo.platform.account.mapper.RolePermissionMapper;
import com.demo.platform.account.service.IRolePermissionService;
import com.demo.platform.commons.service.BaseServiceImpl;
import com.demo.platform.commons.status.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统角色权限表 服务实现类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

    public void updateStatus(Long rolePermissionId, Status status, User user) {
        RolePermission update = new RolePermission();
        update.setId(rolePermissionId);
        update.setStatus(status);
        update.setOperationId(user.getId());
        update.setUpdateTime(LocalDateTime.now());
        updateById(update);
    }
}
