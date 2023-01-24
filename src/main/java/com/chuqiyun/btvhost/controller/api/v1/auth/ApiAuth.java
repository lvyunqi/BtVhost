package com.chuqiyun.btvhost.controller.api.v1.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuqiyun.btvhost.annotation.AdminLoginCheck;
import com.chuqiyun.btvhost.dao.ApikeyDao;
import com.chuqiyun.btvhost.dao.ApitokenDao;
import com.chuqiyun.btvhost.entity.Apikey;
import com.chuqiyun.btvhost.entity.Apitoken;
import com.chuqiyun.btvhost.service.ApikeyService;
import com.chuqiyun.btvhost.utils.ObjectUtil;
import com.chuqiyun.btvhost.utils.RedisUtil;
import com.chuqiyun.btvhost.utils.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.chuqiyun.btvhost.bt.BtUtil.getMd5;
import static com.chuqiyun.btvhost.constant.RedisConstant.PREFIX_VHOST_API_TOKEN;
import static com.chuqiyun.btvhost.utils.AppUtil.getAppId;
import static com.chuqiyun.btvhost.utils.AppUtil.getAppSecret;
import static com.chuqiyun.btvhost.utils.IpUtil.*;
import static com.chuqiyun.btvhost.utils.IpUtil.getRealAddressByIP;
import static com.chuqiyun.btvhost.utils.RedisUtil.isRedisConnected;

/**
 * @author mryunqi
 * @date 2023/1/24
 */
@RestController
@RequiredArgsConstructor
public class ApiAuth {
    private final ApikeyService apikeyService;
    private final ApikeyDao apikeyDao;
    private final ApitokenDao apitokenDao;
    @PostMapping(value = "/api/v1/getToken")
    @ResponseBody
    public ResponseResult<String> apiAuth(@RequestParam(value = "apiKey") String apiKey,@RequestParam(value = "secretKey") String secretKey) throws Exception {
        QueryWrapper<Apikey> apikeyQueryWrapper = new QueryWrapper<>();
        apikeyQueryWrapper.eq("apiKey",apiKey);
        Apikey api = apikeyService.getOne(apikeyQueryWrapper);
        if (ObjectUtil.isNull(api)){
            return ResponseResult.fail(ResponseResult.RespCode.API_AUTH_ERROR);
        }
        QueryWrapper<Apikey> secretKeyQueryWrapper = new QueryWrapper<>();
        secretKeyQueryWrapper.eq("secretKey",secretKey);
        api = apikeyService.getOne(secretKeyQueryWrapper);
        if (ObjectUtil.isNull(api)){
            return ResponseResult.fail(ResponseResult.RespCode.API_AUTH_ERROR);
        }
        long nowTime = System.currentTimeMillis();
        String token = getMd5(apiKey+secretKey+nowTime+"chuqiyun");
        Apitoken apitoken = new Apitoken();
        apitoken.setToken(token);
        apitoken.setAppid(api.getAppid());
        apitoken.setEndtime(nowTime + 180000);
        apitokenDao.insert(apitoken);
        if (isRedisConnected()){
            RedisUtil.set(PREFIX_VHOST_API_TOKEN+token, String.valueOf(nowTime+180000),nowTime+180000);
        }
        return ResponseResult.ok(token);
    }

    @AdminLoginCheck
    @PostMapping(value = "/api/addApi")
    public ResponseResult<String> addApi() {
        String apiKey = getAppId();
        String secretKey = getAppSecret(apiKey);
        Apikey api = new Apikey();
        api.setApikey(apiKey);
        api.setSecretkey(secretKey);
        api.setCreatedate(new Date());
        apikeyDao.insert(api);
        return ResponseResult.ok();
    }
}
