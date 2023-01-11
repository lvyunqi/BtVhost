package com.chuqiyun.btvhost.utils;

import com.chuqiyun.btvhost.entity.User;
import io.jsonwebtoken.Claims;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * @author mryunqi
 * @date 2023/1/11
 */
public class ServletUtil {
    public static User getLoginMember(HttpServletRequest request) {
        String token = getCookie(request,"token");
        if (null == token) {
            return null;
        }
        Claims jwt = JwtProvider.decodeToken(token);
        if(null == jwt){
            return null;
        }
        User members = new User();
        members.setId((Integer) jwt.get("UUID"));
        return members;
    }
// 获取cookie中的属性值

    public static String getCookie(HttpServletRequest request, String var) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(var)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
