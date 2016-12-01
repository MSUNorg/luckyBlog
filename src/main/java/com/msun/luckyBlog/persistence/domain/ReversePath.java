/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.domain;

/**
 * 封装反向代理服务器的路径信息
 * 
 * @author zxc Dec 1, 2016 6:36:41 PM
 */
public class ReversePath {

    private String picFilePath; // 图片文件所在基础路径,形如"/home/jcala/blog/static/img/"
    private String picUrlPath; // 图片的链接基础路径,形如"http://127.0.0.1:8090/static/img/"
    private String cssUrlPath; // css文件链接基础路径,形如"http://127.0.0.1:8090/static/css/"
    private String jsUrlPath;  // js文件链接基础路径,形如"http://127.0.0.1:8090/static/js/"

    public String getPicFilePath() {
        return picFilePath;
    }

    public void setPicFilePath(String picFilePath) {
        this.picFilePath = picFilePath;
    }

    public String getPicUrlPath() {
        return picUrlPath;
    }

    public void setPicUrlPath(String picUrlPath) {
        this.picUrlPath = picUrlPath;
    }

    public String getCssUrlPath() {
        return cssUrlPath;
    }

    public void setCssUrlPath(String cssUrlPath) {
        this.cssUrlPath = cssUrlPath;
    }

    public String getJsUrlPath() {
        return jsUrlPath;
    }

    public void setJsUrlPath(String jsUrlPath) {
        this.jsUrlPath = jsUrlPath;
    }
}
