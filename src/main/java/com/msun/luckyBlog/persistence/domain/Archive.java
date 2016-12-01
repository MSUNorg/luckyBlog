/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.domain;

import java.util.List;

/**
 * 前端页面archives显示的博客列表对应的实体
 * 
 * @author zxc Dec 1, 2016 6:35:53 PM
 */
public class Archive {

    private int            year; // 年份
    private List<BlogView> list; // 此年份的博客列表

    public Archive() {

    }

    public Archive(int year, List<BlogView> list) {
        this.year = year;
        this.list = list;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<BlogView> getList() {
        return list;
    }

    public void setList(List<BlogView> list) {
        this.list = list;
    }
}
