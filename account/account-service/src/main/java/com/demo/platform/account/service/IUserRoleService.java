package com.demo.platform.account.service;


import com.demo.platform.account.entity.User;
import com.demo.platform.account.entity.UserRole;
import com.demo.platform.commons.service.IBaseService;
import com.demo.platform.commons.status.Status;

import java.util.List;

/**
 * <p>
 * 系统用户角色表 服务类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
public interface IUserRoleService extends IBaseService<UserRole> {

    void updateStatus(Long userRoleId, Status status, User user);

    void createBatch(User user, List<UserRole> roleList);
}
