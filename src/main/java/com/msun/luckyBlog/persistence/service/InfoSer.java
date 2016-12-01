/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.msun.luckyBlog.persistence.domain.Info;
import com.msun.luckyBlog.persistence.mapper.InfoMapper;

/**
 * @author zxc Dec 1, 2016 6:37:57 PM
 */
@Service
public class InfoSer {

    private static final Logger LOGGER        = LoggerFactory.getLogger(InfoSer.class);

    private static final int    MODIFYPASSSUC = 0;                                     // 修改密码成功
    private static final int    PASSERROE     = 1;                                     // 密码错误
    private static final int    SySTEMERROE   = 2;                                     // 系统错误

    @Autowired
    private InfoMapper          infoMapper;

    // 获取username,email,github,twitter,avatar信息
    @Cacheable(value = "profileOfInfo", key = "1")
    public Info getInfo() throws RuntimeException {
        return infoMapper.select();
    }

    // 登录验证
    public boolean login(Info user) {
        int num = 0;
        try {
            num = infoMapper.selectByPw(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 修改密码之前进行的密码验证
    public boolean checkPass(String oldPass) {
        int num = infoMapper.selectByOldPass(oldPass);
        return num > 0;
    }

    // 后台管理中更新sername,email,github,twitter,avatar信息
    @CacheEvict(value = "profileOfInfo", key = "1")
    public boolean updateInfo(Info info) {
        boolean result = true;
        try {
            infoMapper.update(info);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result = false;
        }
        return result;
    }

    // 修改密码
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int modifyPw(String oldPass, String newPass) {
        int result;
        if (checkPass(oldPass)) {
            try {
                infoMapper.updataPass(newPass);
                result = MODIFYPASSSUC;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                result = SySTEMERROE;
            }
        } else {
            result = PASSERROE;
        }
        return result;
    }

    // 登录成功后添加session
    public void addSession(HttpServletRequest request, Info info) {
        HttpSession session = request.getSession(true);
        session.setAttribute("cur_user", info);
        session.setMaxInactiveInterval(600);
    }

    // 退出登录或者超时之后销毁session
    public void destroySession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.removeAttribute("cur_user");
    }

    // 后台管理中获取简历的markdown文本
    public String getResumeMd() {
        String md = "";
        try {
            md = infoMapper.selectMd();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return md;
    }

    // 后台管理更新简历
    @CacheEvict(value = "resumeView", key = "1")
    public boolean updateResume(Info info) {
        boolean result = true;
        try {
            infoMapper.updateResume(info);
        } catch (Exception e) {
            result = false;
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    // 前端about页面获取简历的html文本
    @Cacheable(value = "resumeView", key = "1")
    public String getResumeView() throws RuntimeException {
        return infoMapper.selectResume();
    }

    // 更新头像
    @CacheEvict(value = "profileOfInfo", key = "1")
    public void updateAvatar(String avatar) throws RuntimeException {
        infoMapper.updateAvater(avatar);
    }
}
