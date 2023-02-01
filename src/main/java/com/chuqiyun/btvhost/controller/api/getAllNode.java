package com.chuqiyun.btvhost.controller.api;

import com.chuqiyun.btvhost.annotation.AdminLoginCheck;
import com.chuqiyun.btvhost.dao.ServernodeDao;
import com.chuqiyun.btvhost.model.GetNode;
import com.chuqiyun.btvhost.utils.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class getAllNode {
    private final ServernodeDao servernodeDao;
    @AdminLoginCheck
    @GetMapping(value = "/admin/getAllNode")
    //查询所有节点
    public ResponseResult<HashMap<String, Object>> AllNode(Model model) {
        return GetNode.getAllNode(servernodeDao);
    }
}
