package com.chuqiyun.btvhost.controller;

import com.chuqiyun.authorization.entity.LicenseCheckModel;
import com.chuqiyun.btvhost.BtVhostApplication;
import com.chuqiyun.btvhost.license.LicenseVerify;
import com.chuqiyun.btvhost.model.AbstractServerInfos;
import com.chuqiyun.btvhost.model.LinuxServerInfos;
import com.chuqiyun.btvhost.model.WindowsServerInfos;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mryunqi
 * @date 2023/1/9
 */
@RestController
public class testDemo {
    @GetMapping("/restart")
    public void restart() {
        BtVhostApplication.restart();
    }


    @GetMapping(value = "/getServerInfos")
    public LicenseCheckModel getServerInfos(@RequestParam(value = "osName",required = false) String osName) {
        //操作系统类型
        if(StringUtils.isBlank(osName)){
            osName = System.getProperty("os.name");
        }
        osName = osName.toLowerCase();

        AbstractServerInfos abstractServerInfos = null;

        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        }else{//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos.getServerInfos();
    }
}
