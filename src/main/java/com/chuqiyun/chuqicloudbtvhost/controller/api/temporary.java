package com.chuqiyun.chuqicloudbtvhost.controller.api;

import com.chuqiyun.chuqicloudbtvhost.utils.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mryunqi
 * @date 2023/1/10
 */
@RestController
public class temporary {
    @PostMapping("/api/temporary/addAdmin")
    public ResponseResult<String> addAdmin(){
        return ResponseResult.ok("");
    }
}
