package com.chuqiyun.btvhost.utils;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author mryunqi
 * @date 2023/1/20
 */
@Slf4j
public class IpUtil {
    private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
    private static final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);

    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";
    private static final String GET_IP_URL = "https://ip.useragentinfo.com/json?ip={ip}";
    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static boolean isInnerIP(String ipAddress) {
        boolean isInnerIp = false;
        long ipNum = getIpNum(ipAddress);
        /*
          私有IP：A类  10.0.0.0    - 10.255.255.255
                 B类  172.16.0.0  - 172.31.255.255
                 C类  192.168.0.0 - 192.168.255.255
                 D类  127.0.0.0   - 127.255.255.255
         */
        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");
        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");
        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");
        long dBegin = getIpNum("127.0.0.0");
        long dEnd = getIpNum("127.255.255.255");
        isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd)
                || isInner(ipNum, cBegin, cEnd) || isInner(ipNum, dBegin, dEnd)
                || ipAddress.equals("localhost");
        return isInnerIp;
    }

    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);
        return a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
    }

    private static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }
    public static String getRealAddressByIP(String ip) {
        // 内网不查询
        if (isInnerIP(ip)) {
            return "内网IP";
        }
        try {
            //String rspStr = sendGet(IP_URL, "ip=" + ip + "&json=true", "GBK");
            ResponseEntity<String> rspStr = sendGet(GET_IP_URL, ip, "UTF-8");
            String resultString = String.valueOf(rspStr.getBody());
            if (StringUtils.isEmpty(resultString)) {
                log.error("获取地理位置异常 {}", ip);
                return UNKNOWN;
            }
            JSONObject obj = JSONObject.parseObject(resultString);
            String region = obj.getString("country");
            String city = obj.getString("city");
            return String.format("%s-%s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return UNKNOWN;
    }
    public static ResponseEntity<String> sendGet(String url, String param, String contentType) {
        BufferedReader in = null;
        ResponseEntity<String> responseEntity = null;
        try {
            RestTemplate restTemplate=new RestTemplate();
            Map<String,String> params=new HashMap<>();
            params.put("ip",param);
            responseEntity=restTemplate.getForEntity(url,String.class,params);
            //log.info("sendGet - {}" , urlNameString);
            //log.info("recv - {}" , result);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return responseEntity;
    }

    public static boolean isIP(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return false;
        }
        if (!IPv4_PATTERN.matcher(ip).matches()) {
            return false;
        }
        String[] parts = ip.split("\\.");
        try {
            for (String segment : parts) {
                if (Integer.parseInt(segment) > 255 ||
                        (segment.length() > 1 && segment.startsWith("0"))) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static String getDomainIp(String domain) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(domain);
        return address.getHostAddress();
    }

    public static Boolean isDomain(String str){
        String regex = "^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$";
        return str.matches(regex);
    }
}
