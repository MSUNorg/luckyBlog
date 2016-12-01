/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.domain;

import java.sql.Timestamp;

/**
 * @author zxc Dec 1, 2016 6:36:23 PM
 */
public class Project {

    private int       id;  // id
    private String    name; // 项目名称
    private String    url; // 项目url地址，例如https://github.com/jcalaz/jcalaBlog
    private String    tech; // 项目所用技术
    private String    desp; // 项目描述
    private Timestamp date; // 项目创建时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
