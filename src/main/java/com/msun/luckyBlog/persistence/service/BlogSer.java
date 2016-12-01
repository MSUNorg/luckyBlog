/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.service;

import java.sql.Date;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.msun.luckyBlog.persistence.domain.Archive;
import com.msun.luckyBlog.persistence.domain.BlogView;
import com.msun.luckyBlog.persistence.mapper.BlogMapper;
import com.msun.luckyBlog.support.MSUNUtils;

/**
 * @author zxc Dec 1, 2016 6:37:45 PM
 */
@Service
public class BlogSer {

    static final Logger log = LoggerFactory.getLogger(BlogSer.class);

    @Autowired
    private BlogMapper  blogMapper;

    // 后台管理中更新博客时获取博客的markdown文本，title，tags
    public BlogView adminGetBlog(int vid) {
        return blogMapper.selectAdmin(vid);
    }

    // 添加博客
    @Caching(evict = { @CacheEvict(value = "archives", key = "1"), @CacheEvict(value = "tagList", key = "1"),
            @CacheEvict(value = "archivePageNum", key = "1") })
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean addBlog(BlogView blogView) {
        blogView.setDate(new Date(System.currentTimeMillis()));
        boolean result = true;
        try {
            blogMapper.insertBlog(blogView);
        } catch (Exception e) {
            log.error(e.getMessage());
            result = false;
        }
        if (result) {
            try {
                addViewTag(blogView.getTags(), blogView.getVid());
            } catch (Exception e) {
                log.error(e.getMessage());
                result = false;
            }
        }
        return result;
    }

    // 用于后台管理中显示博客列表
    public List<BlogView> getBlogPage(int id) {
        int start = (id - 1) * 10;
        return blogMapper.selectTenBlogs(start);
    }

    // 后台管理中获取博客页面总数
    public int adminGetPageNum() {
        int num = blogMapper.selectBlogNum();
        return num % 10 == 0 ? num / 10 : num / 10 + 1;
    }

    // 后台管理更新博客操作
    @Caching(evict = { @CacheEvict(value = "archives", key = "1"), @CacheEvict(value = "tagList", key = "1") })
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean updateBlog(BlogView blogView) {
        boolean result = true;
        try {
            blogMapper.updateBlogView(blogView);
        } catch (Exception e) {
            result = false;
            log.error(e.getMessage());
        }
        if (result) {
            try {
                updateViewTag(blogView.getTags(), blogView.getVid());
            } catch (Exception e) {
                log.error(e.getMessage());
                result = false;
            }
        }
        return result;
    }

    // 后台管理删除博客操作
    @Caching(evict = { @CacheEvict(value = "archives", key = "1"), @CacheEvict(value = "tagList", key = "1"),
            @CacheEvict(value = "archivePageNum", key = "1") })
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean deleteBlogById(int vid) {
        boolean result = true;
        try {
            blogMapper.deleteBlogView(vid);
        } catch (Exception e) {
            log.error(e.getMessage());
            result = false;
        }
        return result;
    }

    // 前端标签页面获取标签列表
    @Cacheable(value = "tagList", key = "1")
    public List<String> getTagList() {
        return blogMapper.selectTags();
    }

    // 前端archives页面获取archive
    @Cacheable(value = "archives", condition = "#page==1", key = "1")
    public List<Archive> getArchive(int page) {
        int start = (page - 1) * 12;
        return bv2Ar(blogMapper.selectArc(start));
    }

    // 前端显示获取archive页面总数
    @Cacheable(value = "archivePageNum", key = "1")
    public int getArchiveNum() {
        int blogNum = blogMapper.selectBlogNum();
        return blogNum % 12 == 0 ? blogNum / 12 : blogNum / 12 + 1;
    }

    // 前端博客显示界面获取博客html文本，对应post/id
    public BlogView getBlog(int vid) {
        return blogMapper.selectView(vid);
    }

    // 前端获取上一篇博客的id和title
    public BlogView getPrevBlog(int vid) {
        return blogMapper.selectPreView(vid);
    }

    // 前端获取下一篇博客的id和title
    public BlogView getNextBlog(int vid) {
        BlogView blogView = null;
        try {
            blogView = blogMapper.selectNextView(vid);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return blogView;
    }

    // 前端根据tag名称获取博客列表
    public List<BlogView> getBlogByTag(String tagName) {
        List<BlogView> views = new ArrayList<>();
        List<Integer> vids = blogMapper.selectVidBytag(tagName);
        for (int vid : vids) {
            BlogView view = blogMapper.selectTagView(vid);
            if (view != null) {
                view.setVid(vid);
                String monthDay = MSUNUtils.getEdate(view.getDate());
                view.setMonthDay(monthDay);
                views.add(view);
            }
        }
        return views;
    }

    private List<Archive> bv2Ar(List<BlogView> views) {
        List<Archive> archives = new ArrayList<>();
        Map<Integer, Archive> years2Ar = new HashMap<>();
        for (BlogView view : views) {
            Date date = view.getDate();
            view.setMonthDay(MSUNUtils.getEdate(date));
            int year = MSUNUtils.getYear(date);
            if (years2Ar.containsKey(year)) {
                years2Ar.get(year).getList().add(view);
            } else {
                Archive archive = new Archive(year, new ArrayList<BlogView>());
                years2Ar.put(year, archive);
                archive.getList().add(view);
                archives.add(archive);
            }
        }
        return archives;
    }

    private void addViewTag(String tagStr, int vid) {
        List<String> tagList = getTagList(tagStr);
        for (String tag : tagList) {
            blogMapper.insertViewTag(tag, vid);
        }
    }

    private void updateViewTag(String tagStr, int vid) {
        blogMapper.deleteViewTag(vid);
        List<String> tagList = getTagList(tagStr);
        for (String tag : tagList) {
            blogMapper.insertViewTag(tag, vid);
        }
    }

    public static List<String> getTagList(String tagStr) {
        List<String> tagList = new ArrayList<>();
        StringTokenizer token = new StringTokenizer(tagStr, ",");
        while (token.hasMoreTokens()) {
            tagList.add(token.nextToken());
        }
        return tagList;
    }
}
