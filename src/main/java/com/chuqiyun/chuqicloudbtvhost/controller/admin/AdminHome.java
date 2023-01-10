package com.chuqiyun.chuqicloudbtvhost.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mryunqi
 * @date 2023/1/10
 */
@Controller
public class AdminHome {

    @RequestMapping(value = "/admin")
    public String admin() {
        return "/admin/index";
    }

}
