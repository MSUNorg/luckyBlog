/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.domain;

/**
 * 上传图片回显提示的实体类
 * 
 * @author zxc Dec 1, 2016 6:37:00 PM
 */
public class UploadPic {

    private int    success; // 0 表示上传失败，1 表示上传成功
    private String message; // 图片上传提示信息,包括上传成功或上传失败及错误信息等
    private String url;    // 图片上传成功后返回的地址

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
