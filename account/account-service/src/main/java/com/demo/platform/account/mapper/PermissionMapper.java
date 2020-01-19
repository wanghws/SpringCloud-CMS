package com.demo.platform.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.SearchPermissionDTO;
import com.demo.platform.account.entity.Permission;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统权限表 Mapper 接口
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    Page<Permission> findPermissionPage(Page<Permission> page,
                                           @Param("permission") SearchPermissionDTO searchPermissionDTO);
}
