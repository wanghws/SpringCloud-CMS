package com.demo.platform.commons.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.platform.commons.entity.BaseEntity;
import org.modelmapper.ModelMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by github.com/wanghws on 2019-02-21.
 */
public class BaseServiceImpl<D extends BaseMapper<T>,T extends BaseEntity> extends ServiceImpl<D,T> implements IBaseService<T> {

    @Resource
    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public <T> T map(Object source, Class<T> clazz){
        return modelMapper.map(source,clazz);
    }

    public <T> List<T> map(List<?> source, Class<T> clazz){
        return source.stream().map(u -> modelMapper.map(u,clazz)).collect(Collectors.toList());
    }

    public <T> Page<T> map(Page<?> source, Class<T> clazz){
        List<T> recordList = source.getRecords().stream().map(u -> modelMapper.map(u,clazz)).collect(Collectors.toList());
        source.setRecords(null);
        Page<T> newPage = new Page<>();
        modelMapper.map(source,newPage);
        newPage.setRecords(recordList);
        return newPage;
    }

}
