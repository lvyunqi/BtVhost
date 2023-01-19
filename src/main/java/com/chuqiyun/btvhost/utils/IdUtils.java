package com.chuqiyun.btvhost.utils;

import java.util.UUID;

/**
 * @author mryunqi
 * @date 2023/1/19
 */
public class IdUtils {
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 生成不重复的UUID，多线程下保证不重复
     */
    public static String getUUIDByThread(){
        return UUID.randomUUID().toString().replace("-", "") + Thread.currentThread().getId();
    }

}
