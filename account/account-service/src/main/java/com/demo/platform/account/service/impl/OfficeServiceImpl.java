package com.demo.platform.account.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.OfficeRecordDTO;
import com.demo.platform.account.dto.SearchOfficeDTO;
import com.demo.platform.account.entity.User;
import com.demo.platform.account.service.IOfficeService;
import com.demo.platform.account.entity.Office;
import com.demo.platform.account.mapper.OfficeMapper;
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
 * 系统部门表 服务实现类
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Service
public class OfficeServiceImpl extends BaseServiceImpl<OfficeMapper, Office> implements IOfficeService {

    @Resource
    private OfficeMapper officeMapper;

    public Page<OfficeRecordDTO> findOfficePage(Page<Office> officePage, SearchOfficeDTO searchOfficeDTO) {
        return this.map(officeMapper.findOfficePage(officePage,searchOfficeDTO),OfficeRecordDTO.class);
    }

    public void updateOrSave(Office office, User user) {
        Long id = office.getId();
        if (id == null) {
            office.setCreateTime(LocalDateTime.now());
            office.setUpdateTime(LocalDateTime.now());
            office.setOperationId(user.getId());
            office.setStatus(Status.NORMAL);
            save(office);
        } else {
            office.setUpdateTime(LocalDateTime.now());
            office.setOperationId(user.getId());
            updateById(office);
        }
    }

    public void updateStatus(Long id, Status status,User user) {
        Office update = new Office();
        update.setId(id);
        update.setStatus(status);
        update.setUpdateTime(LocalDateTime.now());
        update.setOperationId(user.getId());
        updateById(update);
    }

    public List<Map<String,Object>> treeList(Office rootOffice){
        List<Office> list = this.lambdaQuery()
                .eq(Office::getParentId,rootOffice.getId())
                .eq(Office::getStatus, Status.NORMAL)
                .list();
        Map<String,Object> childrenMap = null;
        List<Map<String,Object>> childrenList = new ArrayList<>();

        for(Office childrenOffice:list){
            int count = this.lambdaQuery()
                    .eq(Office::getParentId,childrenOffice.getId())
                    .eq(Office::getStatus, Status.NORMAL)
                    .count();

            childrenMap = new HashMap<>();
            childrenMap.put("id",childrenOffice.getId()+"");
            childrenMap.put("label",childrenOffice.getName());

            if (count <=  0){
                childrenList.add(childrenMap);
                continue;
            }

            List<Map<String,Object>> nodeList = treeList(childrenOffice);
            childrenMap.put("children",nodeList);

            childrenList.add(childrenMap);
        }
        return childrenList;
    }

}
