package com.chuqiyun.btvhost.config;

import com.chuqiyun.btvhost.interceptor.LicenseCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mryunqi
 * @date 2023/1/9
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        LicenseCheckInterceptor licenseCheckInterceptor = new LicenseCheckInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(licenseCheckInterceptor);
        // 拦截路径
        loginRegistry.addPathPatterns("/**");
        // 排除路径
        loginRegistry.excludePathPatterns("/getServerInfos");
        // 排除资源请求
        // 排除资源请求
        loginRegistry.excludePathPatterns("/css/**");
        loginRegistry.excludePathPatterns("/js/**");
        loginRegistry.excludePathPatterns("/images/**");
        loginRegistry.excludePathPatterns("/favicon.ico");
        loginRegistry.excludePathPatterns("/fonts/**");
    }
}
