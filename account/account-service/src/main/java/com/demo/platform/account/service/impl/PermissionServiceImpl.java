package com.demo.platform.account.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.PermissionRecordDTO;
import com.demo.platform.account.dto.SearchPermissionDTO;
import com.demo.platform.account.entity.Permission;
import com.demo.platform.account.entity.User;
import com.demo.platform.account.mapper.PermissionMapper;
import com.demo.platform.account.service.IPermissionService;
import com.demo.platform.commons.service.BaseServiceImpl;
import com.demo.platform.commons.status.Status;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统权限表 服务实现类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    @Resource
    private PermissionMapper permissionMapper;

    public Page<PermissionRecordDTO> findPermissionPage(Page<Permission> permissionPage, SearchPermissionDTO searchPermissionDTO) {
        return this.map(permissionMapper.findPermissionPage(permissionPage,searchPermissionDTO),PermissionRecordDTO.class);
    }

    public void updateStatus(Long id, Status status, User User) {
        Permission update = new Permission();
        update.setId(id);
        update.setStatus(status);
        update.setUpdateTime(LocalDateTime.now());
        update.setOperationId(User.getId());
        updateById(update);
    }

    public List<Map<String,Object>> treeList(Permission rootPermission){
        List<Permission> list = this.lambdaQuery()
                .eq(Permission::getParentId,rootPermission.getId())
                .eq(Permission::getStatus, Status.NORMAL)
                .list();
        Map<String,Object> childrenMap = null;
        List<Map<String,Object>> childrenList = new ArrayList<>();

        for(Permission childrenPermission:list){
            int count = this.lambdaQuery()
                    .eq(Permission::getParentId,childrenPermission.getId())
                    .eq(Permission::getStatus, Status.NORMAL)
                    .count();

            childrenMap = new HashMap<>();
            childrenMap.put("id",childrenPermission.getId()+"");
            childrenMap.put("label",childrenPermission.getName());

            if (count <=  0){
                childrenList.add(childrenMap);
                continue;
            }

            List<Map<String,Object>> nodeList = treeList(childrenPermission);
            childrenMap.put("children",nodeList);

            childrenList.add(childrenMap);
        }
        return childrenList;
    }
}
