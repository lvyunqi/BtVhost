package com.chuqiyun.btvhost.controller.admin;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuqiyun.btvhost.constant.RedisConstant;
import com.chuqiyun.btvhost.entity.User;
import com.chuqiyun.btvhost.service.UserService;
import com.chuqiyun.btvhost.utils.*;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.chuqiyun.btvhost.utils.JwtProvider.createToken;
import static com.chuqiyun.btvhost.utils.RedisUtil.isRedisConnected;
import static com.chuqiyun.btvhost.utils.RegexUtil.isPhone;
import static com.chuqiyun.btvhost.utils.ServletUtil.getCookie;

/**
 * @author mryunqi
 * @date 2023/1/10
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminLogin {
    private final UserService userService;
    @RequestMapping(value = "/{admin}/login")
    public String adminLogin(@PathVariable("admin") String adminPath) {
        return adminPath+"/login";
    }

    @RequestMapping(value = "/admin/logout")
    public String adminLogOut(HttpServletRequest request){
        String token = getCookie(request,"token");
        if (token == null){
            return "admin/login";
        }
        Claims jwt = JwtProvider.decodeToken(token);
        if(null == jwt){
            return "admin/login";
        }
        if (!isRedisConnected()){
            log.warn("您未配置Redis或当前Redis不在状态，请及时检查修复，以免造成严重安全隐患！");
            return "admin/login";
        }
        RedisUtil.delete(RedisConstant.PREFIX_VHOST_REFRESH_TOKEN + jwt.get("UUID"));
        return "admin/login";
    }

    @PostMapping(value = "/admin/loginDo")
    @ResponseBody
    public ResponseResult<String> adminToLogin(@RequestBody JSONObject data,HttpServletResponse response){
        if (isPhone(data.getString("username"))){
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("mobil",data.getString("username"));
            return getStringResponseResult(data, response, wrapper);
        }else {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("email",data.getString("username"));
            return getStringResponseResult(data, response, wrapper);
        }
    }

    @NotNull
    private ResponseResult<String> getStringResponseResult(@RequestBody JSONObject data, HttpServletResponse response, QueryWrapper<User> wrapper) {
        User user = userService.getOne(wrapper);
        if (ObjectUtil.isNull(user)){
            return ResponseResult.fail("用户不存在！");
        }
        if (!user.getPassword().equals(EncryptUtil.md5(data.getString("password")))){
            return ResponseResult.fail("账号或密码不正确!");
        }
        int expire = 0;
        if (data.getInteger("rememberme") == 0){
            expire = 1800;
        }else {
            expire = 432000;
        }
        String jwt = createToken(data.getString("username"),expire);
        Cookie cookie = new Cookie("token", jwt);
        cookie.setMaxAge(expire);
        response.addCookie(cookie);
        return ResponseResult.ok();
    }
}
