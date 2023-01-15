package com.chuqiyun.btvhost.utils;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author mryunqi
 * @date 2023/1/15
 */
public class JsonUtil {
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
}
