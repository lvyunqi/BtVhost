package com.chuqiyun.chuqicloudbtvhost.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.chuqiyun.chuqicloudbtvhost.license.LicenseVerify;
import com.chuqiyun.chuqicloudbtvhost.utils.ResponseResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mryunqi
 * @date 2023/1/9
 */
public class LicenseCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        LicenseVerify licenseVerify = new LicenseVerify();

        //校验证书是否有效
        boolean verifyResult = licenseVerify.verify();

        if(verifyResult){
            return true;
        }else{
            response.setContentType("application/json;charset=utf-8");
            JSONObject obj = new JSONObject();
            obj.put("code", 20401);
            obj.put("msg", "系统未授权");
            response.getWriter().print(obj);
            response.getWriter().flush();
            return false;
        }
    }
}
