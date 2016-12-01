/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.domain;

import java.sql.Date;

/**
 * 博客页面的存储，包括id,日期，标题和博客页面
 * 
 * @author zxc Dec 1, 2016 6:36:01 PM
 */
public class BlogView {

    private Integer vid;     // id
    private Date    date;    // 博客创建日期
    private String  title;   // 博客标题，不可为空
    private String  article; // 博客内容的html文本
    private String  tags;    // 标签，不同标签以,隔开
    private String  md;      // 博客内容的markdown文本
    private String  monthDay; // 形如"Oct 04",为了方便archives页面显示，并不对应数据库的任何一列

    public BlogView() {

    }

    public BlogView(Integer vid, Date date, String title, String article, String tags, String md, String monthDay) {
        this.vid = vid;
        this.date = date;
        this.title = title;
        this.article = article;
        this.tags = tags;
        this.md = md;
        this.monthDay = monthDay;
    }

    public BlogView(String title, String tags, String md) {
        this.title = title;
        this.tags = tags;
        this.md = md;
    }

    public BlogView(int vid, String title, String tags) {
        this.vid = vid;
        this.title = title;
        this.tags = tags;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(String monthDay) {
        this.monthDay = monthDay;
    }
}
