package com.chuqiyun.btvhost.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.chuqiyun.btvhost.annotation.AdminLoginCheck;
import com.chuqiyun.btvhost.annotation.ApiCheck;
import com.chuqiyun.btvhost.entity.User;
import com.chuqiyun.btvhost.license.LicenseVerify;
import com.chuqiyun.btvhost.service.ApitokenService;
import com.chuqiyun.btvhost.utils.ServletUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.chuqiyun.btvhost.utils.AppUtil.checkToken;

/**
 * @author mryunqi
 * @date 2023/1/9
 */

public class LicenseCheckInterceptor implements HandlerInterceptor {
    private final ApitokenService apitokenService;

    public LicenseCheckInterceptor(ApitokenService apitokenService) {
        this.apitokenService = apitokenService;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        LicenseVerify licenseVerify = new LicenseVerify();

        //RedisUtil = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()).getBean(RedisUtil.class);
        //判断请求的方法上是否有注解
        boolean haveAnnotation = handler.getClass().isAssignableFrom(HandlerMethod.class);
        //String url = request.getRequestURI();

        //校验证书是否有效
        boolean verifyResult = licenseVerify.verify();

        if(verifyResult){
            if (haveAnnotation){
                // 强转
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                // 获取方法
                Method method = handlerMethod.getMethod();
                if (method.isAnnotationPresent(AdminLoginCheck.class)) {
                    //如果有注解，判断是否是MyAnnotation
                    AdminLoginCheck ma = method.getAnnotation(AdminLoginCheck.class);
                    //如果存在该注解
                    if (null != ma) {
                        //获得名为member的对象
                        User members = ServletUtil.getLoginMember(request);
                        if (null == members) {
                            //如果不是转发到/index上
                            response.sendRedirect("/admin/login");
                            return false;
                        }

                    }
                } else if (method.isAnnotationPresent(ApiCheck.class)) {
                    ApiCheck apiCheck = method.getAnnotation(ApiCheck.class);
                    //如果存在该注解
                    if (null != apiCheck) {
                        Boolean checkApi = checkToken(request,apitokenService);
                        if (!checkApi){
                            response.setContentType("application/json;charset=utf-8");
                            JSONObject obj = new JSONObject();
                            obj.put("code", 5010002);
                            obj.put("msg", "API鉴权失败");
                            response.getWriter().print(obj);
                            response.getWriter().flush();
                            return false;
                        }else {
                            return true;
                        }
                    }
                }else {
                    return true;
                }
            }
            return true;
        }else{
            if (haveAnnotation){
                // 强转
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                // 获取方法
                Method method = handlerMethod.getMethod();
                if (!method.isAnnotationPresent(ApiCheck.class)) {
                    response.sendRedirect("/authError");
                    return false;
                }
                ApiCheck apiCheck = method.getAnnotation(ApiCheck.class);
                //如果存在该注解
                if (null != apiCheck) {
                    response.setContentType("application/json;charset=utf-8");
                    JSONObject obj = new JSONObject();
                    obj.put("code", 20401);
                    obj.put("msg", "系统未授权");
                    response.getWriter().print(obj);
                    response.getWriter().flush();
                }
            }
            return false;
        }
    }
}
