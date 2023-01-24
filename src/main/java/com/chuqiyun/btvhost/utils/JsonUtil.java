package com.chuqiyun.btvhost.utils;

import com.google.gson.Gson;
import com.google.gson.JsonNull;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

/**
 * @author mryunqi
 * @date 2023/1/15
 */
public class JsonUtil {
    private static Gson gson=new Gson();
    public static String readJsonFile(String filePath) {
        String jsonStr = "";
        try {
            File jsonFile = new File(filePath);
            Reader reader = new InputStreamReader(Files.newInputStream(jsonFile.toPath()), StandardCharsets.UTF_8);
            int ch = 0;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * @MethodName : toJson
     * @Description : 将对象转为JSON串，此方法能够满足大部分需求
     * @param src
     *            :将要被转化的对象
     * @return :转化后的JSON串
     */
    public static String toJson(Object src) {
        if (src == null) {
            return gson.toJson(JsonNull.INSTANCE);
        }
        return gson.toJson(src);
    }

    /**
     * @param json
     * @param classOfT
     * @return
     * @MethodName : fromJson
     * @Description : 用来将JSON串转为对象，但此方法不可用来转带泛型的集合
     */
    public static <T> Map<String, Object> fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, (Type) classOfT);
    }

    /**
     * @MethodName : fromJson
     * @Description : 用来将JSON串转为对象，此方法可用来转带泛型的集合，如：Type为
     *              new TypeToken<List<T>>(){}.getType()
     *              ，其它类也可以用此方法调用，就是将List<T>替换为你想要转成的类
     * @param json
     * @param typeOfT
     * @return
     */
    public static Object fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

}
