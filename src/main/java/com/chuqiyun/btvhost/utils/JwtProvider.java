package com.chuqiyun.btvhost.utils;

import com.chuqiyun.btvhost.constant.RedisConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

/**
 * @author mryunqi
 * @date 2023/1/10
 */
@Slf4j
@Component
public class JwtProvider {
    private static final int expire = 1800;

    /**
     * 生成token
     *
     * @param userId 用户id
     */
    public static String createToken(Object userId) {
        if (RedisUtil.exists(RedisConstant.PREFIX_SHIRO_CACHE + userId)) {
            RedisUtil.delete(RedisConstant.PREFIX_SHIRO_CACHE + userId);
        }
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        RedisUtil.set(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + userId, currentTimeMillis, expire);
        return createToken(userId, "VhostWeb-Authorization");
    }

    /**
     * 生成token
     *
     * @param userId   用户id
     * @param clientId 用于区别客户端，如移动端，网页端，此处可根据业务自定义
     */
    public static String createToken(Object userId, String clientId) {
        //@Value("${jwt.expire}")
        Date validity = new Date((new Date()).getTime() + expire * 1000);
        return Jwts.builder()
                // 代表这个JWT的主体，即它的所有人
                .setSubject(String.valueOf(userId))
                // 代表这个JWT的签发主体
                .setIssuer("Vhost Master Control System")
                // 是一个时间戳，代表这个JWT的签发时间；
                .setIssuedAt(new Date())
                // 代表这个JWT的接收对象
                .setAudience(clientId)
                // 放入用户id
                .claim("UUID", userId)
                // 自定义信息
                .claim("Auth", "Admin")
                .signWith(SignatureAlgorithm.HS512, getSecretKey())
                .setExpiration(validity)
                .compact();
    }

    /**
     * 校验token
     */
    public static boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("无效的token：" + authToken);
        }
        return false;
    }

    /**
     * 解码token
     */
    public static Claims decodeToken(String token) {
        if (validateToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
            // 客户端id
            String clientId = claims.getAudience();
            // 用户id
            Object userId = claims.get("UUID");
            log.info("[{}] UUID:{} 请求鉴权",clientId,userId);
            Date currentTimeMillis = claims.getIssuedAt();
            // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
            if (RedisUtil.exists(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + userId)) {
                // 获取RefreshToken的时间戳
                Date currentTimeMillisRedis = new Date(Long.parseLong(Objects.requireNonNull(RedisUtil.get(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + userId))));
                // 获取AccessToken时间戳，与RefreshToken的时间戳对比 1670407779316
                if (currentTimeMillis.toString().equals(currentTimeMillisRedis.toString())) {
                    log.info("UUID:{},鉴权成功", userId);
                    return claims;
                }
                log.error("UUID:{},Redis鉴权失败！",userId);
                return null;
            }
            log.error("UUID:{},鉴权失败！",userId);
        }
        return null;
    }

    private static String getSecretKey() {
        //@Value("${jwt.secret}")
        String secret = "UkingMryunqi434253";
        return Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }
}
