package com.demo.platform.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.SearchUserDTO;
import com.demo.platform.account.dto.UserRecordDTO;
import com.demo.platform.account.dto.UserDetailDTO;
import com.demo.platform.account.entity.User;
import com.demo.platform.account.entity.UserRole;
import com.demo.platform.commons.service.IBaseService;
import com.demo.platform.commons.status.Status;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
public interface IUserService extends IBaseService<User> {
    User findUserByLoginName(String loginName);
    UserDetailDTO getUserByLoginName(String loginName);
    Page<UserRecordDTO> findUserPage(Page<User> UserPage, SearchUserDTO User);
    void updateStatus(Long UserId, Status status, String loginName, User user);
    List<UserRole> userRoleByUser(Long UerId);
}
