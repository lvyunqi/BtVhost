package com.chuqiyun.btvhost.controller.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.chuqiyun.btvhost.utils.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mryunqi
 * @date 2023/1/10
 */
@RestController
public class ChartMap {
    @PostMapping(value = "/api/chart/map")
    public ResponseResult<JSONObject> login(){
        JSONObject jsonObject = new JSONObject();
        List<String> data= new ArrayList<>();
        data.add("北京");
        data.add("内蒙古");
        data.add("东京");
        JSONArray jsonArray = new JSONArray();
        int i = 0;
        for (String city:data){
            JSONObject cityJson = new JSONObject();
            cityJson.put("city",city);
            cityJson.put("nodeNum",5);
            jsonArray.add(cityJson);
            i++;
        }
        jsonObject.put("dataCenter","深圳");
        jsonObject.put("dataNode",jsonArray);
        jsonObject.put("cityNum",i);
        jsonObject.put("nodeNum",15);
        return ResponseResult.ok(jsonObject);
    }
}
