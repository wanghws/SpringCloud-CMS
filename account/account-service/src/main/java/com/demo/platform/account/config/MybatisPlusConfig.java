package com.demo.platform.account.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.modelmapper.ModelMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by github.com/wanghws on 2019-02-21.
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.demo.platform.account.mapper")
public class MybatisPlusConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
