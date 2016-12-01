/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zxc Dec 1, 2016 5:51:58 PM
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class Launcher extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Launcher.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Launcher.class, args);
    }
}
