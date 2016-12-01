/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.msun.luckyBlog.persistence.domain.Info;
import com.msun.luckyBlog.persistence.service.BlogSer;
import com.msun.luckyBlog.persistence.service.FileUploadSer;
import com.msun.luckyBlog.persistence.service.InfoSer;
import com.msun.luckyBlog.persistence.service.ProjectSer;

/**
 * 前端页面显示的控制器 共包括archives,login,projects,tags,about,post,login这几个页面
 * 
 * @author zxc Dec 1, 2016 6:35:40 PM
 */
@Controller
public class FontEndController {

    @Autowired
    private BlogSer       blogSer;
    @Autowired
    private ProjectSer    projectSer;
    @Autowired
    private InfoSer       infoSer;
    @Autowired
    private FileUploadSer fileUploadSer;

    @GetMapping("/archives/{page}")
    public ModelAndView archives(@PathVariable int page) {
        return new ModelAndView("archives")//
        .addObject("info", infoSer.getInfo())//
        .addObject("archives", blogSer.getArchive(page))//
        .addObject("pageNum", blogSer.getArchiveNum())//
        .addObject("current", page);
    }

    @GetMapping("/projects")
    public ModelAndView projects() {
        return new ModelAndView("projects")//
        .addObject("info", infoSer.getInfo());
    }

    @GetMapping("/projects/{page}")
    public ModelAndView projectPage(@PathVariable int page) {
        return new ModelAndView("projects")//
        .addObject("info", infoSer.getInfo())//
        .addObject("projects", projectSer.getPros(page))//
        .addObject("pageNum", projectSer.getPageNum())//
        .addObject("current", page);
    }

    @GetMapping("/tags")
    public ModelAndView tags() {
        return new ModelAndView("tags")//
        .addObject("info", infoSer.getInfo())//
        .addObject("tags", blogSer.getTagList());
    }

    @GetMapping("/tags/{tagName}")
    public ModelAndView tagName(@PathVariable String tagName) {
        return new ModelAndView("tagView")//
        .addObject("info", infoSer.getInfo())//
        .addObject("views", blogSer.getBlogByTag(tagName))//
        .addObject("tagName", tagName);
    }

    @GetMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about")//
        .addObject("info", infoSer.getInfo())//
        .addObject("resume", infoSer.getResumeView());
    }

    @GetMapping("/post/{id}")
    public ModelAndView post(@PathVariable int id) {
        return new ModelAndView("post")//
        .addObject("info", infoSer.getInfo())//
        .addObject("prev", blogSer.getPrevBlog(id))//
        .addObject("next", blogSer.getNextBlog(id))//
        .addObject("article", blogSer.getBlog(id).getArticle());
    }

    @GetMapping("/")
    public ModelAndView welcome() {
        return new ModelAndView("index")//
        .addObject("info", infoSer.getInfo());
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        model.addAttribute("avatar", infoSer.getInfo().getAvatar());
        String result = request.getParameter("result");
        if (result != null && result.equals("fail")) {
            model.addAttribute("success", 0);
        }
        return "login";
    }

    @PostMapping("/login.action")
    public String doLogin(Info user, HttpServletRequest request) {
        boolean result = infoSer.login(user);
        if (result) {
            infoSer.addSession(request, user);
            return "redirect:/admin";
        } else {
            return "redirect:/login?result=fail";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        infoSer.destroySession(request);
        return "redirect:/login";
    }

    @GetMapping(value = "/pic/{dir}/{picName:.+}")
    public ResponseEntity<byte[]> gainUserAvatar(@PathVariable String dir, @PathVariable String picName)
                                                                                                        throws Exception {
        return fileUploadSer.gainPic(dir, picName);
    }
}
