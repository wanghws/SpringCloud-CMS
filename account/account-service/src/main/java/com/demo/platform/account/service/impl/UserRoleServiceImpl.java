package com.demo.platform.account.service.impl;


import com.demo.platform.account.entity.User;
import com.demo.platform.account.entity.UserRole;
import com.demo.platform.account.mapper.UserRoleMapper;
import com.demo.platform.account.service.IUserRoleService;
import com.demo.platform.commons.service.BaseServiceImpl;
import com.demo.platform.commons.status.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统用户角色表 服务实现类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    public void updateStatus(Long userRoleId, Status status, User user) {
        UserRole update = new UserRole();
        update.setId(userRoleId);
        update.setStatus(status);
        update.setUpdateTime(LocalDateTime.now());
        update.setOperationId(user.getId());
        updateById(update);
    }

    public void createBatch(User roleUser, List<UserRole> roleList) {
        saveBatch(roleList);
    }
}
