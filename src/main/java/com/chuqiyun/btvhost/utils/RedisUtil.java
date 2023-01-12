package com.chuqiyun.btvhost.utils;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author mryunqi
 * @date 2023/1/10
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {
    /**
     * 是否开启redis缓存  true开启   false关闭
     */
    private static final boolean open=true;

    private static RedisTemplate<String, Object> redisTemplate;
    private static ValueOperations<String, String> valueOperations;
    private static HashOperations<String, String, Object> hashOperations;
    private static ListOperations<String, Object> listOperations;
    private static SetOperations<String, Object> setOperations;
    private static ZSetOperations<String, Object> zSetOperations;

    @Autowired(required = false)
    public RedisUtil( RedisTemplate<String, Object> redisTemplate, ValueOperations<String, String> valueOperations, HashOperations<String, String, Object> hashOperations, ListOperations<String, Object> listOperations, SetOperations<String, Object> setOperations, ZSetOperations<String, Object> zSetOperations) {
        RedisUtil.redisTemplate = redisTemplate;
        RedisUtil.valueOperations = valueOperations;
        RedisUtil.hashOperations = hashOperations;
        RedisUtil.listOperations = listOperations;
        RedisUtil.setOperations = setOperations;
        RedisUtil.zSetOperations = zSetOperations;
    }

    /**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 7200;

    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;


    public static boolean exists(String key) {
        if (!open) {
            return false;
        }

        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public static void set(String key, String value, long expire) {
        if (!open) {
            return;
        }
        try {
            valueOperations.set(key, value);

        } catch (Exception e0) {
            System.out.println(e0.getMessage());
        }
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public static void set(String key, String value) {
        if (!open) {
            return;
        }
        try {

            valueOperations.set(key, value);

        } catch (Exception e0) {
            System.out.println(e0.getMessage());
        }
    }

    public static <T> T get(String key, Class<T> clazz, long expire) {
        if (!open) {
            return null;
        }

        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : JSONObject.parseObject(value, clazz);
    }

    public static <T> T get(String key, Class<T> clazz) {
        if (!open) {
            return null;
        }

        return get(key, clazz, NOT_EXPIRE);
    }

    public static String get(String key, long expire) {
        if (!open) {
            return null;
        }

        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public static String get(String key) {
        if (!open) {
            return null;
        }

        return get(key, NOT_EXPIRE);
    }

    public static void delete(String key) {
        if (!open) {
            return;
        }

        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    public static void delete(String... keys) {
        if (!open) {
            return;
        }

        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }

    public static void deletePattern(String pattern) {
        if (!open) {
            return;
        }

        Set<String> keys = redisTemplate.keys(pattern);
        if (keys != null && keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * Object转成JSON数据
     */

    public static Set<String> keys(String key){
        return redisTemplate.keys(key);
    }
    public static void clear(){
        Set<String> keys = keys("*");
        if(keys!=null){

            for (String next : keys) {
                delete(next);
            }
        }
    }

    public static boolean isRedisConnected() {
        try {
            return Boolean.TRUE.equals(redisTemplate.execute((RedisCallback<Boolean>) connection -> "PONG".equals(connection.ping())));
        } catch (Exception e) {
            return false;
        }
    }
}