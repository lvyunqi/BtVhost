package com.chuqiyun.chuqicloudbtvhost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mryunqi
 * @date 2023/1/9
 */
@Controller
public class rootDemo {
    @RequestMapping(value = "/admin")
    public String admin() {
        return "/admin/index";
    }
}
