package com.chuqiyun.btvhost.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuqiyun.btvhost.dao.ServernodeDao;
import com.chuqiyun.btvhost.entity.Servernode;
import com.chuqiyun.btvhost.utils.ResponseResult;
import java.util.HashMap;
import java.util.List;

public class GetNode {
    public static ResponseResult<HashMap<String,Object>> getAllNode(ServernodeDao servernodeDao){
        int total;
        QueryWrapper<Servernode> wrapper = new QueryWrapper<>();
        wrapper.select("serverNode");
        List<Servernode> servernodeList = servernodeDao.selectList(null);
        total=servernodeList.size();
        HashMap<String,Object> resultList = new HashMap<>();
        resultList.put("rows",servernodeList);
        resultList.put("total",total);
        return ResponseResult.ok(resultList);

    }
}
