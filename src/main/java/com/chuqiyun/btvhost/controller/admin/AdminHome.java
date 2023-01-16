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
        model.addAttribute("title", "控制台");
        model.addAttribute("page","adminHome");
        model.addAttribute("page_tab","adminHome");
        return "/admin/hostlist";
    }

    @AdminLoginCheck
    @RequestMapping(value = "/admin/addhost")
    public String addhost(Model model){
        model.addAttribute("title", "控制台");
        model.addAttribute("page","adminHome");
        model.addAttribute("page_tab","adminHome");
        return "/admin/addhost";
    }
}
