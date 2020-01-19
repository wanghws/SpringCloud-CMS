package com.demo.platform.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.SearchUserDTO;
import com.demo.platform.account.entity.User;
import com.demo.platform.account.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
public interface UserMapper extends BaseMapper<User> {

    Page<User> findUserPage(Page<User> sysUserPage, @Param("user") SearchUserDTO user);

    List<UserRole> userRolesByUser(@Param("userId") Long userId);
}
