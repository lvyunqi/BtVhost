package com.chuqiyun.chuqicloudbtvhost.config;

import com.chuqiyun.chuqicloudbtvhost.interceptor.LicenseCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

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
        loginRegistry.excludePathPatterns("/admin");
        loginRegistry.excludePathPatterns("/admin/**");
        // 排除资源请求
        loginRegistry.excludePathPatterns("/static/**");
    }
}
