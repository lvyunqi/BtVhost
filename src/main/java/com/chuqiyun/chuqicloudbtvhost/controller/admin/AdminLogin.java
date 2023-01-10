package com.chuqiyun.chuqicloudbtvhost.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mryunqi
 * @date 2023/1/10
 */
@Controller
public class AdminLogin {
    @RequestMapping(value = "/admin/login")
    public String adminLogin() {
        return "/admin/login";
    }
}
