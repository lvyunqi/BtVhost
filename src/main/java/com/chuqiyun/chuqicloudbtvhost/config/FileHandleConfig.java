package com.chuqiyun.chuqicloudbtvhost.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * @author mryunqi
 * @date 2023/1/7
 */
@Configuration
public class FileHandleConfig implements WebMvcConfigurer {
    /**
     * 跨域
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowedMethods("POST","GET","PUT","OPTIONS","DELETE")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String gitPath = Objects.requireNonNull(path).getParentFile().getParent() + File.separator + "public" + File.separator;
        registry.addResourceHandler("/**").addResourceLocations("file:" +gitPath + File.separator + "static" + File.separator);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
