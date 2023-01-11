package com.chuqiyun.btvhost.config;

import com.chuqiyun.btvhost.utils.EncryptUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileNotFoundException;

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
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String rootPath = System.getProperty("user.dir");
        String gitPath = rootPath + File.separator + "public" + File.separator;
        registry.addResourceHandler("/**").addResourceLocations("file:" +gitPath + File.separator + "static" + File.separator);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
