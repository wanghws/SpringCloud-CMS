package com.demo.platform.account.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.demo.platform.account.interceptor.LoginInterceptor;
import com.demo.platform.account.interceptor.UserArgumentResolver;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author github.com/wanghws
 * @date 20200109
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Lazy
    @Resource
    private LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/account/login","/v2/**","/test/**")
                .order(1);

    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ServletWebArgumentResolverAdapter(new UserArgumentResolver()));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer(){
        return builder -> builder.featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }
}
