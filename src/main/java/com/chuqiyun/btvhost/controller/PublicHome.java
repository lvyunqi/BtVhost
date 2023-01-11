package com.chuqiyun.btvhost.controller;

import com.chuqiyun.btvhost.annotation.ApiCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mryunqi
 * @date 2023/1/10
 */
@Controller
public class PublicHome {

    @RequestMapping(value = "/")
    public String home() {
        return "other/home";
    }
}
