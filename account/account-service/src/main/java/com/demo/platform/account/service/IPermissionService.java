package com.demo.platform.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.PermissionRecordDTO;
import com.demo.platform.account.dto.SearchPermissionDTO;
import com.demo.platform.account.entity.Permission;
import com.demo.platform.account.entity.User;
import com.demo.platform.commons.service.IBaseService;
import com.demo.platform.commons.status.Status;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统权限表 服务类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
public interface IPermissionService extends IBaseService<Permission> {

    Page<PermissionRecordDTO> findPermissionPage(Page<Permission> permissionPage, SearchPermissionDTO searchPermissionDTO);

    void updateStatus(Long id, Status status, User user);

    List<Map<String,Object>> treeList(Permission rootPermission);
}
