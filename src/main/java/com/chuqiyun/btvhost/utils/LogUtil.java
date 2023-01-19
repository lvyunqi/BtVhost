package com.chuqiyun.btvhost.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author mryunqi
 * @date 2023/1/17
 */
public class LogUtil {
    public static Date logTimeConvert(String requestDate) throws ParseException {
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
        Date date = sdf.parse(requestDate);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf3.parse(sdf2.format(date));
    }
}
