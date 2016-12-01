/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.service;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.msun.luckyBlog.persistence.domain.Info;
import com.msun.luckyBlog.persistence.domain.SystemSetting;
import com.msun.luckyBlog.persistence.domain.UploadPic;
import com.msun.luckyBlog.support.FileTools;
import com.msun.luckyBlog.support.exception.CtrlExceptionHandler;

/**
 * @author zxc Dec 1, 2016 6:37:51 PM
 */
@Service
public class FileUploadSer {

    static final Logger   log = LoggerFactory.getLogger(CtrlExceptionHandler.class);

    private InfoSer       infoSer;

    private SystemSetting setting;

    @Autowired
    public FileUploadSer(InfoSer infoSer, SystemSetting setting) {
        this.infoSer = infoSer;
        this.setting = setting;
    }

    public UploadPic uploadPic(HttpServletRequest request) {

        String picUrl = "";
        try {
            picUrl = FileTools.updatePic("/pic/", setting.getPicHome(), request);
        } catch (Exception e) {
            log.warn("上传图片时发生错误:" + e.getLocalizedMessage());
        }
        final UploadPic upload = new UploadPic();
        if ("".equals(picUrl)) {
            upload.setSuccess(0);
            upload.setMessage("Upload picture fail!");
            upload.setUrl("");
            return upload;
        }
        upload.setSuccess(1);
        upload.setMessage("Upload picture success!");
        upload.setUrl(picUrl);
        return upload;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Info updateAvatar(HttpServletRequest request) {
        String url = uploadPic(request).getUrl();
        if (!"".equals(url)) {
            infoSer.updateAvatar(url);
        }
        return infoSer.getInfo();
    }

    public ResponseEntity<byte[]> gainPic(String dir, String picName) {
        File file = new File(setting.getPicHome() + File.separatorChar + dir + File.separatorChar + picName);
        byte[] bytes;
        try {
            bytes = FileTools.readFileToByteArray(file);
        } catch (Exception e) {
            log.warn(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", picName);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
