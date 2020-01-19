package com.demo.platform.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.OfficeRecordDTO;
import com.demo.platform.account.dto.SearchOfficeDTO;
import com.demo.platform.account.entity.Office;
import com.demo.platform.account.entity.User;
import com.demo.platform.commons.service.IBaseService;
import com.demo.platform.commons.status.Status;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统部门表 服务类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
public interface IOfficeService extends IBaseService<Office> {

    Page<OfficeRecordDTO> findOfficePage(Page<Office> officePage, SearchOfficeDTO searchOfficeDTO);

    void updateOrSave(Office office, User user);

    void updateStatus(Long id, Status status, User user);

    List<Map<String,Object>> treeList(Office rootOffice);
}
