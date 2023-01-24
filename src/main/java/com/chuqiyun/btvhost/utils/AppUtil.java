package com.chuqiyun.btvhost.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuqiyun.btvhost.constant.RedisConstant;
import com.chuqiyun.btvhost.entity.Apitoken;
import com.chuqiyun.btvhost.service.ApitokenService;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

import static com.chuqiyun.btvhost.utils.RedisUtil.isRedisConnected;

/**
 * @author mryunqi
 * @date 2023/1/24
 */
public class AppUtil {
    //生成 app_secret 密钥
    private final static String SERVER_NAME = "mazhq_abc123";
    private final static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * @Description: <p>
     * 短8位UUID思想其实借鉴微博短域名的生成方式，但是其重复概率过高，而且每次生成4个，需要随即选取一个。
     * 本算法利用62个可打印字符，通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，然后通过模62操作，结果作为索引取出字符，
     * 这样重复率大大降低。
     * 经测试，在生成一千万个数据也没有出现重复，完全满足大部分需求。
     * </p>
     * @date 2019/8/27 16:16
     */
    public static String getAppId() {
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    /**
     * <p>
     * 通过appId和内置关键词生成APP Secret
     * </P>
     * @date 2019/8/27 16:32
     */
    public static String getAppSecret(String appId) {
        try {
            String[] array = new String[]{appId, SERVER_NAME};
            StringBuilder sb = new StringBuilder();
            // 字符串排序
            Arrays.sort(array);
            for (String s : array) {
                sb.append(s);
            }
            String str = sb.toString();
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuilder hexstr = new StringBuilder();
            String shaHex = "";
            for (byte b : digest) {
                shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Boolean checkToken(HttpServletRequest request, ApitokenService apitokenService){
        String accessToken = request.getHeader("Authorization");
        if(accessToken == null){
            return false;
        }
        if (isRedisConnected()){
            return RedisUtil.exists(RedisConstant.PREFIX_VHOST_API_TOKEN + accessToken);
        }
        QueryWrapper<Apitoken> apiWrapper = new QueryWrapper<>();
        apiWrapper.eq("token",accessToken);
        Apitoken apiToken= apitokenService.getOne(apiWrapper);
        if (ObjectUtil.isNull(apiToken)){
            return false;
        }
        long time = apiToken.getEndtime();
        return time >= System.currentTimeMillis();
    }

    public static void main(String[] args) {
        String appId = getAppId();
        String appSecret = getAppSecret(appId);
        System.out.println("appId: "+appId);
        System.out.println("appSecret: "+appSecret);
    }
}

