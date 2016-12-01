/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.domain;

/**
 * 个人信息
 * 
 * @author zxc Dec 1, 2016 6:36:10 PM
 */
public class Info {

    private String username; // 用户名
    private String password; // md5加密后的密码
    private String email;   // 邮箱,默认为#
    private String github;  // github地址，默认为#
    private String twitter; // twitter地址，默认为#
    private String md;      // 简历的markdown文本，为了admin管理时能够回显
    private String resume;  // 简历的html文本
    private String Avatar;  // 头像地址

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }
}
