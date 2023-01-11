package com.chuqiyun.btvhost.annotation;

import java.lang.annotation.*;

/**
 * @author mryunqi
 * @date 2023/1/11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminLoginCheck {
}
