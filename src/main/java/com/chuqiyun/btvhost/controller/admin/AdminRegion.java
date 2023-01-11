package com.chuqiyun.btvhost.controller.admin;

import com.chuqiyun.btvhost.annotation.AdminLoginCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mryunqi
 * @date 2023/1/11
 */
@Controller
@RequiredArgsConstructor
public class AdminRegion {
    @AdminLoginCheck
    @RequestMapping(value = "/admin/region")
    public String adminRegion(Model model) {
        model.addAttribute("title", "地区管理");
        model.addAttribute("page","region");
        model.addAttribute("page_tab","region");
        return "/admin/region";
    }

}
