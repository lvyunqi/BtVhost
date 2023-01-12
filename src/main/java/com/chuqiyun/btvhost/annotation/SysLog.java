package com.chuqiyun.btvhost.annotation;

import java.lang.annotation.*;

/**
 * @author mryunqi
 * @date 2023/1/12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String operMoudle() default "";//操作接口
    String operMethod() default "";//操作类型
    String operDes() default "";//操作描述
}
