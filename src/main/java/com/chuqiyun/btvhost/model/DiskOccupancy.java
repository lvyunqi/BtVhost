package com.chuqiyun.btvhost.model;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuqiyun.btvhost.constant.BtConstant;
import com.chuqiyun.btvhost.entity.Servernode;
import com.chuqiyun.btvhost.service.ServernodeService;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

import static com.chuqiyun.btvhost.bt.BtUtil.getMd5;
import static com.chuqiyun.btvhost.bt.BtUtil.sendPost;

/**
 * @author mryunqi
 * @date 2023/1/25
 */
@Slf4j
public class DiskOccupancy {
    public static int allDiskValue(int regionId, ServernodeService servernodeService) {
        QueryWrapper<Servernode> nodes = new QueryWrapper<>();
        nodes.eq("groupClass",regionId);
        List<Servernode> nodesList = servernodeService.list(nodes);
        if (nodesList.size() == 0){
            return 0;
        }
        int valueResult = 0;
        int nodeDiskNum = 0;
        for (Servernode node : nodesList){
            if (node.getSatus() == 1){
                continue;
            }
            String host = node.getHost();
            int port = node.getPort();
            String btSign = node.getSkey();
            StringBuilder url = new StringBuilder();
            if (node.getServerssl() == 0){
                url.append("http://").append(host).append(":").append(port).append(BtConstant.GET_DISK_INFO);
            }else {
                url.append("https://").append(host).append(":").append(port).append(BtConstant.GET_DISK_INFO);
            }
            String responseText = null;
            try{
                long timestamp = System.currentTimeMillis();
                String md5Sign = getMd5(btSign);
                String temp = timestamp+md5Sign;
                String token = getMd5(temp);

                String json = "request_time="+timestamp+"&request_token="+token;
                responseText = sendPost(String.valueOf(url),json);
            }catch (Exception e) {
                e.printStackTrace();
            }
            if (responseText == null){
                return 0;
            }
            try {
                JSONObject jsonObject = JSONObject.parseObject(responseText);
                if (jsonObject == null){
                    return 0;
                }
                if (!jsonObject.getBoolean("status")){
                    return 0;
                }
            }catch (Exception e) {
                log.error(String.valueOf(e));
            }
            JSONArray jsonArray = JSON.parseArray(responseText);
            List<JSONObject> list = jsonArray.toJavaList(JSONObject.class);
            if (list.size() == 1){
                JSONObject nodeDisk = list.get(0);
                List<String> size = nodeDisk.getList("size",String.class);
                String value = size.get(size.size() - 1).replace("%","");
                valueResult = valueResult + Integer.parseInt(value);
                nodeDiskNum++;
            }else {
                for (JSONObject nodeDisk : list) {
                    List<String> size = nodeDisk.getList("size", String.class);
                    String value = size.get(size.size() - 1).replace("%", "");
                    valueResult = valueResult + Integer.parseInt(value);
                    nodeDiskNum++;
                }
            }
        }
        if(nodeDiskNum == 0){
            return 0;
        }
        valueResult = valueResult / nodeDiskNum;
        return valueResult;
    }
}
