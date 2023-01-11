package com.chuqiyun.btvhost.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mryunqi
 * @date 2023/1/11
 */
public class RegexUtil {
    /**
     * 手机号验证
     *
     * @param phoneNumber
     * @return 验证通过返回true
     */
    public static boolean isPhone(String phoneNumber) {
        String regex = "^((13[0-9])|(14(0|[5-7]|9))|(15([0-3]|[5-9]))|(16(2|[5-7]))|(17[0-8])|(18[0-9])|(19([0-3]|[5-9])))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }
}
