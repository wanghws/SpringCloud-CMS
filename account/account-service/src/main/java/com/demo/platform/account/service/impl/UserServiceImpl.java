package com.demo.platform.account.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.SearchUserDTO;
import com.demo.platform.account.dto.UserDetailDTO;
import com.demo.platform.account.dto.UserRecordDTO;
import com.demo.platform.account.entity.Permission;
import com.demo.platform.account.entity.User;
import com.demo.platform.account.entity.UserRole;
import com.demo.platform.account.mapper.UserMapper;
import com.demo.platform.account.service.IRoleService;
import com.demo.platform.account.service.IUserRoleService;
import com.demo.platform.account.service.IUserService;
import com.google.common.collect.Lists;
import com.demo.platform.commons.constants.Constants;
import com.demo.platform.commons.service.BaseServiceImpl;
import com.demo.platform.commons.status.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private IRoleService roleService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private IUserRoleService userRoleService;

    @Cacheable(value = Constants.CACHE_ACCOUNT, key = "#loginName", unless="#result == null")
    public User findUserByLoginName(String loginName){
        return this.lambdaQuery()
                .eq(User::getLoginName,loginName)
                .eq(User::getStatus, Status.NORMAL)
                .one();
    }

    @CacheEvict(value = Constants.CACHE_ACCOUNT, key = "#loginName")
    public UserDetailDTO getUserByLoginName(String loginName){
        log.info("login user: "+loginName);
        User user = this.lambdaQuery()
                .eq(User::getLoginName,loginName)
                .eq(User::getStatus, Status.NORMAL)
                .one();

        if(null == user)return null;

        Permission permissionQuery = new Permission();
        permissionQuery.setUserId(user.getId());
        List<Permission> roleList = roleService.findRolePermissionsByUser(permissionQuery);
        
        Set<String> authorities = new HashSet<>();
        for(Permission permission:roleList){
            authorities.add(permission.getPermission());
        }
        UserDetailDTO userDetailDTO = this.map(user, UserDetailDTO.class);
        userDetailDTO.setRoles(authorities);
        return userDetailDTO;
    }

    public Page<UserRecordDTO> findUserPage(Page<User> userPage, SearchUserDTO searchUserDTO) {
        Page<User> page = userMapper.findUserPage(userPage,searchUserDTO);
        return map(page,UserRecordDTO.class);
    }

    @Transactional
    @CacheEvict(value = Constants.CACHE_ACCOUNT, key = "#user.loginName")
    public void updateStatus(Long UserId, Status status,String loginName,User user) {
        User update = new User();
        update.setId(UserId);
        update.setStatus(status);
        updateById(update);
        List<UserRole> list = userRoleService.lambdaQuery().eq(UserRole::getUserId, UserId).list();
        List<UserRole> roleList = Lists.newArrayList();
        list.forEach(UserRole -> {
            if (status.equals(UserRole.getStatus())) return;
            UserRole userRole = new UserRole();
            userRole.setId(UserRole.getId());
            userRole.setStatus(status);
            userRole.setUpdateTime(LocalDateTime.now());
            userRole.setOperationId(user.getId());
            roleList.add(userRole);
        });
        if (!CollectionUtils.isEmpty(roleList)) {
            userRoleService.updateBatchById(roleList);
        }
    }

    @Override
    public List<UserRole> userRoleByUser(Long UerId) {
        return userMapper.userRolesByUser(UerId);
    }

}
