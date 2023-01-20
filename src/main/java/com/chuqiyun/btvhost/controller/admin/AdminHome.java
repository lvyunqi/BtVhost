package com.chuqiyun.btvhost.controller.admin;

import com.chuqiyun.btvhost.annotation.AdminLoginCheck;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mryunqi
 * @date 2023/1/10
 */
@Controller
public class AdminHome {

    @AdminLoginCheck
    @RequestMapping(value = "/admin")
    public String admin(Model model) {
        model.addAttribute("title", "控制台");
        model.addAttribute("page","adminHome");
        model.addAttribute("page_tab","adminHome");
        return "/admin/index";
    }
    @AdminLoginCheck
    @RequestMapping(value = "/admin/vhostList")
    public String hostlist(Model model){
        model.addAttribute("title", "主机列表");
        model.addAttribute("page","vhost");
        model.addAttribute("page_tab","vhostList");
        return "/admin/hostlist";
    }

    @AdminLoginCheck
    @RequestMapping(value = "/admin/addVhost")
    public String addhost(Model model){
        model.addAttribute("title", "添加主机");
        model.addAttribute("page","vhost");
        model.addAttribute("page_tab","addVhost");
        return "/admin/addhost";
    }
    @AdminLoginCheck
    @RequestMapping(value = "/admin/help")
    public String help(Model model){
        model.addAttribute("title", "教程与帮助");
        model.addAttribute("page","command");
        model.addAttribute("page_tab","help");
        return "/admin/help";
    }
}
