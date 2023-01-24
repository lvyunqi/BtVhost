package com.chuqiyun.btvhost.controller.api;

import com.chuqiyun.btvhost.annotation.ApiCheck;
import com.chuqiyun.btvhost.utils.ResponseResult;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;

import static com.chuqiyun.btvhost.utils.IpUtil.*;

/**
 * @author mryunqi
 * @date 2023/1/20
 */
@RestController
public class IpGet {
    @PostMapping(value = "/api/getIpLocation")
    @ResponseBody
    public ResponseResult<String> getIpData(@RequestParam(value = "ipAddress") String ip) throws Exception {
        if (!isIP(ip)){
            if (!isDomain(ip)){
                return ResponseResult.fail("提交的参数错误!");
            }
            ip = getDomainIp(ip);
        }
        String ipLocation = getRealAddressByIP(ip);
        return ResponseResult.ok(ipLocation);
    }
}
