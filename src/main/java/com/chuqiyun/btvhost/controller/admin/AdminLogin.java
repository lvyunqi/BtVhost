package com.chuqiyun.btvhost.controller.admin;

import com.alibaba.fastjson2.JSONObject;
import com.chuqiyun.btvhost.entity.User;
import com.chuqiyun.btvhost.utils.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author mryunqi
 * @date 2023/1/10
 */
@Controller
public class AdminLogin {
    @RequestMapping(value = "/admin/login")
    public String adminLogin() {
        return "admin/login";
    }

    @PostMapping(value = "/admin/loginDo")
    @ResponseBody
    public ResponseResult<String> adminToLogin(@RequestBody JSONObject data,HttpServletResponse response){
        System.out.println(data);
        return ResponseResult.ok();
    }
}
