package com.msun.luckyBlog.persistence.domain;

/**
 * 全局配置类，用于储存自定义的全局变量
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
