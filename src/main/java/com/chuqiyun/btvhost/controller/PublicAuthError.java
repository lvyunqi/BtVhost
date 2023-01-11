package com.chuqiyun.btvhost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mryunqi
 * @date 2023/1/11
 */
@Controller
public class PublicAuthError {
    @RequestMapping(value = "/authError")
    public String home() {
        return "other/authError";
    }
}
