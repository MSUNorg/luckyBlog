/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.domain;

/**
 * 全局配置类，用于储存自定义的全局变量
 * 
 * @author zxc Dec 1, 2016 6:36:50 PM
 */
public class SystemSetting {

    private String picHome;

    public SystemSetting() {

    }

    public SystemSetting(String picHome) {
        this.picHome = picHome;
    }

    public String getPicHome() {
        return picHome;
    }

    public void setPicHome(String picHome) {
        this.picHome = picHome;
    }
}
