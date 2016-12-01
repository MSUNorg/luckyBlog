/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.msun.luckyBlog.persistence.domain.Project;
import com.msun.luckyBlog.persistence.mapper.ProjectMapper;

/**
 * @author zxc Dec 1, 2016 6:38:11 PM
 */
@Service
public class ProjectSer {

    @Autowired
    private ProjectMapper projectMapper;

    // 前端project页面获取项目列表
    @Cacheable(value = "projects", condition = "#page==1", key = "1")
    public List<Project> getPros(int page) {
        int start = (page - 1) * 5;
        return projectMapper.select(start);
    }

    // 后台管理添加项目
    @Caching(evict = { @CacheEvict(value = "projects", key = "1"), @CacheEvict(value = "projectPageNum", key = "1") })
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addPro(Project project) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        project.setDate(timestamp);
        projectMapper.insert(project);
    }

    // 后台管理中获取项目列表
    public List<Project> adminGetPros(int page) {
        int start = (page - 1) * 10;
        return projectMapper.adminSelect(start);
    }

    // 后台管理中获取项目页面总数量
    public int adminGetPageNum() {
        int count = projectMapper.count();
        return count % 10 == 0 ? count / 10 : count / 10 + 1;
    }

    // 后台管理删除项目
    @Caching(evict = { @CacheEvict(value = "projects", key = "1"), @CacheEvict(value = "projectPageNum", key = "1") })
    public void deletePro(int id) {
        projectMapper.delete(id);
    }

    // 后台管理获取项目所有信息
    public Project getProById(String idStr) {
        int id = Integer.valueOf(idStr);
        return projectMapper.selectById(id);
    }

    // 后台管理更新博客信息
    @CacheEvict(value = "projects", key = "1")
    public void updatePro(Project project) {
        projectMapper.Update(project);
    }

    // 前端获取项目页面总数
    @Cacheable(value = "projectPageNum", key = "1")
    public int getPageNum() {
        int count = projectMapper.count();
        return count % 5 == 0 ? count / 5 : count / 5 + 1;
    }
}
