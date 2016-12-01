/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.support;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.msun.luckyBlog.persistence.domain.SystemSetting;
import com.msun.luckyBlog.support.interceptor.UserSecurityInterceptor;

/**
 * web页面配置类，拦截器地址在此注册
 * 
 * @author zxc Dec 1, 2016 6:39:26 PM
 */
@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {

    @Value("${pic.home}")
    private String                  picHome;

    @Autowired
    private UserSecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor).addPathPatterns("/admin/**");// 配置登录拦截器拦截路径
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }

    @Bean
    public SystemSetting systemSetting() {
        return new SystemSetting(picHome);
    }
}
