package com.demo.platform.commons.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.platform.commons.entity.BaseEntity;
import org.modelmapper.ModelMapper;

import java.util.List;

/**
 * Created by github.com/wanghws on 2019-02-21.
 */
public interface IBaseService<T extends BaseEntity> extends IService<T> {
    ModelMapper getModelMapper();
    <T> T map(Object source, Class<T> clazz);
    <T> List<T> map(List<?> source, Class<T> clazz);
    <T> Page<T> map(Page<?> source, Class<T> clazz);
}
