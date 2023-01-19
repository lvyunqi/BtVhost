package com.chuqiyun.btvhost.constant;

/**
 * @author mryunqi
 * @date 2023/1/11
 */
public class RedisConstant {
    private RedisConstant() {}

    /**
     * redis-OK
     */
    public static final String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public static final int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public static final int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public static final int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-vhostAdmin:cache:
     */
    public static final String PREFIX_VHOST_CACHE = "vhostAdmin:cache:";

    /**
     * redis-key-前缀-vhostAdmin:access_token:
     */
    public static final String PREFIX_VHOST_ACCESS_TOKEN = "vhostAdmin:access_token:";
    public static final String PREFIX_VHOST_SERVER_TOKEN = "vhostSys:server_token:";
    public static final String PREFIX_VHOST_PROBE_DATA = "vhostSys:probe_data:";

    /**
     * redis-key-前缀-vhostAdmin:refresh_token:
     */
    public static final String PREFIX_VHOST_REFRESH_TOKEN = "vhostAdmin:refresh_token:";

    /**
     * JWT-account:
     */
    public static final String ACCOUNT = "account";

    /**
     * JWT-currentTimeMillis:
     */
    public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 8;

}
