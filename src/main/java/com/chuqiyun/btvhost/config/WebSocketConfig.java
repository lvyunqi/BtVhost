package com.chuqiyun.btvhost.config;

import com.chuqiyun.btvhost.dao.ServernodeDao;
import com.chuqiyun.btvhost.dao.VhostdataDao;
import com.chuqiyun.btvhost.service.ServernodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import com.chuqiyun.btvhost.websocket.WebSocketServer;

import javax.annotation.Resource;

/**
 * @author mryunqi
 * @date 2023/1/7
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    @Autowired
    public void setServernodeService(ServernodeService servernodeService){
        WebSocketServer.servernodeService = servernodeService;
    }
    @Autowired(required=false)
    public void setServernodeDao(VhostdataDao vhostdataDao){
        WebSocketServer.vhostdataDao = vhostdataDao;
    }

    @Resource(name = "taskExecutor")
    public void setTaskExecutor(TaskExecutor executor){
        WebSocketServer.executor = executor;
    }
}
