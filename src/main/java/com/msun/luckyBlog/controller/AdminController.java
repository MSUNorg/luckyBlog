/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.msun.luckyBlog.persistence.domain.BlogView;
import com.msun.luckyBlog.persistence.domain.Info;
import com.msun.luckyBlog.persistence.domain.Project;
import com.msun.luckyBlog.persistence.domain.UploadPic;
import com.msun.luckyBlog.persistence.service.*;

/**
 * @author zxc Dec 1, 2016 6:04:09 PM
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private ProjectSer    projectSer;
    @Autowired
    private MonitorSer    monitorSer;
    @Autowired
    private InfoSer       infoSer;
    @Autowired
    private BlogSer       blogSer;
    @Autowired
    private FileUploadSer uploadSer;

    @GetMapping("/")
    public ModelAndView monitor() {
        return new ModelAndView("admin/monitor")//
        .addObject("freeMemory", monitorSer.getFreeMemory());
    }

    @PostMapping("/file/uplPic.action")
    @ResponseBody
    public UploadPic uploadPic(HttpServletRequest request) {
        return uploadSer.uploadPic(request);
    }

    @PostMapping("/file/uplAva.action")
    public ModelAndView uploadAva(HttpServletRequest request) {
        return new ModelAndView("admin/info")//
        .addObject("info", uploadSer.updateAvatar(request));
    }

    // ************************************************************************************//
    @GetMapping("/project/{page}")
    public ModelAndView project(@PathVariable int page) {
        return new ModelAndView("admin/project")//
        .addObject("current", page)//
        .addObject("pageNum", projectSer.adminGetPageNum())//
        .addObject("proList", projectSer.adminGetPros(page));
    }

    @PostMapping("/addPro.action")
    public String addProject(Project project) {
        projectSer.addPro(project);
        return "redirect:/admin/project/1";
    }

    @GetMapping("/deletePro/{id}")
    public String deletePro(@PathVariable int id) {
        projectSer.deletePro(id);
        return "redirect:/admin/project/1";
    }

    @ResponseBody
    @GetMapping("/pro.json")
    public Project getProJson(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        return projectSer.getProById(idStr);
    }

    @PostMapping("/updPro.action")
    public String updatePro(Project project) {
        projectSer.updatePro(project);
        return "redirect:/admin/project/1";
    }

    // **********************************************************************************//
    /**
     * 后台管理中添加博客页面的控制器
     *
     * @return templates下的admin/blog_add.vm页面
     */
    @GetMapping("/blogAdd")
    public String blogAdd() {
        return "admin/blog_add";
    }

    /**
     * 后台管理中修改博客页面的控制器 由于markdown编辑器的问题，使用了正则匹配update地址，而不是/update/{id}的形式
     *
     * @param id 要修改的博客的id
     * @return templates下的admin/blog_add.vm页面
     */
    @GetMapping("/update{id:\\d+}")
    public String blogModify(@PathVariable int id, Model model) {
        BlogView blogView = blogSer.adminGetBlog(id);
        if (blogView == null) {
            return "error";
        } else {
            blogView.setVid(id);
            model.addAttribute("blog", blogView);
            return "admin/blog_modify";
        }
    }

    /**
     * 添加博客的表单控制器
     *
     * @param view 表单中提交的博客信息,包括标题，标签，md页面，和md转成的html页面
     * @return templates下的result页面，用于提示是否保存博客成功
     */
    @PostMapping("/post.action")
    public String postAction(BlogView view, Model model) {
        boolean result = blogSer.addBlog(view);
        if (result) {
            return "redirect:/admin/blogList/1";
        } else {
            model.addAttribute("targetUrl", "/admin/blog_add");
            model.addAttribute("result", 0);
            return "admin/result";
        }
    }

    /**
     * 更新博客的表单控制器
     *
     * @param view 表单中提交的博客信息,包括id,md页面，和md转成的html页面
     * @return templates下的result页面，用于提示是否保存博客成功
     */
    @PostMapping("/update.action")
    public String update(BlogView view, Model model) {
        boolean result = blogSer.updateBlog(view);
        if (result) {
            model.addAttribute("targetUrl", "/post/" + view.getVid());
            return "redirect:/post/" + view.getVid();
        } else {
            model.addAttribute("targetUrl", "/admin/update" + view.getVid());
            model.addAttribute("result", 0);
            return "admin/result";
        }
    }

    /**
     * 删除博客的控制器
     *
     * @param id 要删除的博客id
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        boolean result = blogSer.deleteBlogById(id);
        if (result) {
            return "redirect:/admin/blogList/1";
        } else {
            model.addAttribute("targetUrl", "/admin/blogList/1");
            model.addAttribute("result", 0);
            return "admin/result";
        }
    }

    /**
     * 所有博客列表的显示界面.
     *
     * @param page 显示的为博客的第几页
     * @return templates下的admin/blog_list.vm页面
     */
    @GetMapping("/blogList/{page}")
    public ModelAndView blogList(@PathVariable int page) {
        return new ModelAndView("admin/blog_list")//
        .addObject("current", page)//
        .addObject("pageNum", blogSer.adminGetPageNum())//
        .addObject("blogList", blogSer.getBlogPage(page));
    }

    // ***************************************************************************************//

    @GetMapping("/info")
    public ModelAndView info() {
        return new ModelAndView("admin/info")//
        .addObject("info", infoSer.getInfo());
    }

    @PostMapping("/info.action")
    public String updateInfo(Info info, Model model) {
        boolean result = infoSer.updateInfo(info);
        model.addAttribute("targetUrl", "/admin/info");
        if (result) {
            model.addAttribute("result", 1);
            return "admin/result";
        } else {
            model.addAttribute("result", 0);
            return "admin/result";
        }
    }

    @PostMapping("/pass.action")
    public String passModify(String old_pass, String new_pass, HttpServletRequest request) {
        int result = infoSer.modifyPw(old_pass, new_pass);
        if (result == 0) {
            infoSer.destroySession(request);
        }
        return "redirect:/admin/info?result=" + result;
    }

    @GetMapping("/resume")
    public ModelAndView resume() {
        return new ModelAndView("admin/resume")//
        .addObject("md", infoSer.getResumeMd());
    }

    @PostMapping("/resume.action")
    public String resumeUpdate(Info info) {
        infoSer.updateResume(info);
        return "redirect:/admin/resume";
    }
}
