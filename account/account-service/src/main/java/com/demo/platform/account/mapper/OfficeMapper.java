package com.demo.platform.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.platform.account.dto.SearchOfficeDTO;
import com.demo.platform.account.entity.Office;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统部门表 Mapper 接口
 * </p>
 *
 * @author github.com/wanghws
 * @since 2019-03-21
 */
public interface OfficeMapper extends BaseMapper<Office> {

    Page<Office> findOfficePage(Page<Office> page,
                                      @Param("office") SearchOfficeDTO searchOfficeDTO);
}
