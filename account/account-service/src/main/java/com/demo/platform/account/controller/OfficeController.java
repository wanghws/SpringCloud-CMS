package com.demo.platform.account.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.OfficeRecordDTO;
import com.demo.platform.account.dto.SearchOfficeDTO;
import com.demo.platform.account.dto.UpdateOfficeDTO;
import com.demo.platform.account.dto.UpdateStatusDTO;
import com.demo.platform.account.entity.Office;
import com.demo.platform.account.entity.User;
import com.demo.platform.account.service.IOfficeService;
import com.demo.platform.account.service.IUserService;
import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.commons.controller.BaseController;
import com.demo.platform.commons.entity.Response;
import com.demo.platform.commons.status.Status;
import com.demo.platform.commons.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统部门表 前端控制器
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
@Api(tags = "部门管理接口")
@Slf4j
@Validated
@RestController
@RequestMapping("office")
public class OfficeController extends BaseController {

    @Resource
    private IOfficeService officeService;

    @Resource
    private IUserService userService;

    @Secured("ROLE_OFFICE_LIST")
    @ApiOperation(value = "部门列表")
    @PostMapping("list")
    public Response<Page<OfficeRecordDTO>> list(@RequestBody @Valid SearchOfficeDTO searchOfficeDTO){

        if (StringUtils.isNotBlank(searchOfficeDTO.getStartTime())){
            searchOfficeDTO.setStartDate(DateUtil.getDateParse(searchOfficeDTO.getStartTime()));
        }
        if (StringUtils.isNotBlank(searchOfficeDTO.getEndTime())){
            searchOfficeDTO.setEndDate(DateUtil.getDateParse(searchOfficeDTO.getEndTime()));
        }

        return Response.ok(officeService.findOfficePage(new Page<>(searchOfficeDTO.getCurrent(), searchOfficeDTO.getSize()),searchOfficeDTO));
    }

    @Secured("ROLE_OFFICE_SAVE")
    @ApiOperation(value = "创建/修改部门信息")
    @PostMapping("save")
    public Response<OfficeRecordDTO> save(@RequestBody @Valid UpdateOfficeDTO updateOfficeDTO, @ApiIgnore User sessionUser){

        if (updateOfficeDTO.getParentId() == null
                && StringUtils.isBlank(updateOfficeDTO.getName())
                && StringUtils.isBlank(updateOfficeDTO.getRemark())) {
            return Response.ok();
        }
        Office office;
        if (updateOfficeDTO.getId() != null){
            office = officeService.getById(updateOfficeDTO.getId());
            if (office == null || office.getStatus() != Status.NORMAL) {
                return Response.fail(GlobalResult.ACCOUNT_OFFICE_NOT_FOUND);
            }
            if (StringUtils.isNotBlank(updateOfficeDTO.getName())) {
                office.setName(updateOfficeDTO.getName());
            }
            if (StringUtils.isNotBlank(updateOfficeDTO.getRemark())) {
                office.setRemark(updateOfficeDTO.getRemark());
            }
            if (updateOfficeDTO.getParentId() != null) {
                office.setParentId(updateOfficeDTO.getParentId());
            }
        } else {
            if (StringUtils.isBlank(updateOfficeDTO.getName())) {
                return Response.fail(GlobalResult.ACCOUNT_NAME_NOT_NULL);
            }
            office = new Office();
            office.setParentId(updateOfficeDTO.getParentId());
            office.setName(updateOfficeDTO.getName());
            office.setRemark(updateOfficeDTO.getRemark());
        }
        officeService.updateOrSave(office,sessionUser);
        Long officeParentId = office.getParentId();
        if (officeParentId != null){
            Office parent = officeService.getById(officeParentId);
            office.setParentName(parent != null ? parent.getName() : "");
        }
        Long operationId = office.getOperationId();
        if (operationId != null && operationId != 0){
            User operation = userService.getById(operationId);
            office.setOperationName(operation != null ? operation.getLoginName() : "");
        }
        return Response.ok(officeService.map(office,OfficeRecordDTO.class));
    }

    @Secured("ROLE_OFFICE_STATUS")
    @ApiOperation(value = "部门状态修改")
    @PostMapping("status")
    public Response<?> status(@RequestBody @Valid UpdateStatusDTO updateStatusDTO, @ApiIgnore User sessionUser){
        Office office = officeService.getById(updateStatusDTO.getId());
        if (office == null) {
            return Response.fail(GlobalResult.ACCOUNT_OFFICE_NOT_FOUND);
        }
        if (updateStatusDTO.getStatus() == office.getStatus()) {
            return Response.fail(GlobalResult.ACCOUNT_STATUS_INVALID);
        }
        officeService.updateStatus(updateStatusDTO.getId(),updateStatusDTO.getStatus(),sessionUser);
        return Response.ok();
    }

    @Secured("ROLE_OFFICE_ALL")
    @ApiOperation(value = "查询全部部门")
    @PostMapping(value = "all")
    public Response<List<OfficeRecordDTO>> all(){
        List<Office> list = officeService.lambdaQuery().eq(Office::getStatus, Status.NORMAL).list();
        return Response.ok(officeService.map(list,OfficeRecordDTO.class));
    }

    @ApiOperation(value = "部门树形菜单")
    @PostMapping(value = "tree")
    public Response<List<Map<String,Object>>> tree(@ApiIgnore User sessionUser){
        Office rootOffice = officeService.getById(sessionUser.getOfficeId());
        if (null == rootOffice)return Response.ok();

        Map<String,Object> rootMap = new HashMap<>();
        List<Map<String,Object>> treeList = new ArrayList<>();
        rootMap.put("id",rootOffice.getId()+"");
        rootMap.put("label",rootOffice.getName());
        rootMap.put("children",officeService.treeList(rootOffice));
        treeList.add(rootMap);
        return Response.ok(treeList);
    }
}

