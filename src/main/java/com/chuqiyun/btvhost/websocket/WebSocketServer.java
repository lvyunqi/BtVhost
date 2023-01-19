package com.chuqiyun.btvhost.websocket;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuqiyun.btvhost.constant.RedisConstant;
import com.chuqiyun.btvhost.dao.ServernodeDao;
import com.chuqiyun.btvhost.dao.VhostdataDao;
import com.chuqiyun.btvhost.entity.Servernode;
import com.chuqiyun.btvhost.service.ServernodeService;
import com.chuqiyun.btvhost.utils.ObjectUtil;
import com.chuqiyun.btvhost.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

import static com.chuqiyun.btvhost.probe.ProbeService.probeDataToMysql;
import static com.chuqiyun.btvhost.utils.IdUtils.getUUIDByThread;
import static com.chuqiyun.btvhost.utils.IdUtils.randomUUID;
import static com.chuqiyun.btvhost.utils.RedisUtil.isRedisConnected;

/**
 * @author mryunqi
 * @date 2023/1/7
 */
@Slf4j
@Component
@ServerEndpoint("/ws/vhost/{token}")
public class WebSocketServer {

/*    private static ExecutorService executorService = Executors.newFixedThreadPool(100);*/
    public static ServernodeService servernodeService;
    public static VhostdataDao vhostdataDao;
    public static TaskExecutor executor;

    /**
     * 每次来一个连接会生成一个本类实例，保存在此，key为ipAddress
     */
    private static final Map<String, WebSocketServer> INSTANCE_MAP = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;


    /**
     * 连接建立成功调用的方法.
     * @param session Session
     * @param ipAddress 前端生成唯一id
     */
    @OnOpen
    public void onOpen(final Session session, @PathParam("token") final String ipAddress) {
        INSTANCE_MAP.put(ipAddress, this);
        this.session = session;
        log.info("Probe连接成功  token:{}", ipAddress);
    }

    /**
     * 连接关闭调用的方法.
     */
    @OnClose
    public void onClose() {
        for (Map.Entry<String, WebSocketServer> entry : INSTANCE_MAP.entrySet()) {
            if (Objects.equals(entry.getValue(), this)) {
                log.info("Probe连接关闭, token:{}", entry.getKey());
                INSTANCE_MAP.remove(entry.getKey());
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法.
     * @param session Session
     * @param message 客户端发送过来的消息
     * @param token 用户唯一id
     */
    @OnMessage
    public void onMessage(final Session session, final String message, final @PathParam("token") String token) {
        log.info("Probe-message:{}, token:{}", message, token);
        String msg = "[Probe "+token+"] 心跳检测";
        if (!message.equals(msg) && !StringUtils.isBlank(message)){
            executor.execute(() ->{
                try {

                    if (isRedisConnected()){
                        if (RedisUtil.exists(RedisConstant.PREFIX_VHOST_REFRESH_TOKEN + token)) {
                            int serverId = Integer.parseInt(Objects.requireNonNull(RedisUtil.get(RedisConstant.PREFIX_VHOST_REFRESH_TOKEN + token)));
                            JSONObject jsonMessage = JSONObject.parseObject(message);
                            jsonMessage.put("serverID",serverId);
                            if(jsonMessage.size() > 1){
                                RedisUtil.set(RedisConstant.PREFIX_VHOST_PROBE_DATA+getUUIDByThread(), jsonMessage.toJSONString());
                            }

                        }else{
                            QueryWrapper<Servernode> serverQueryWrapper = new QueryWrapper<>();
                            serverQueryWrapper.eq("probeToken",token);
                            Servernode server = servernodeService.getOne(serverQueryWrapper);
                            if (!ObjectUtil.isNull(server)){
                                RedisUtil.set(RedisConstant.PREFIX_VHOST_REFRESH_TOKEN + token, String.valueOf(server.getId()));
                                JSONObject jsonMessage = JSONObject.parseObject(message);
                                jsonMessage.put("serverID",server.getId());
                                if(jsonMessage.size() > 1){
                                    RedisUtil.set(RedisConstant.PREFIX_VHOST_PROBE_DATA+getUUIDByThread(), jsonMessage.toJSONString());
                                }
                            }
                        }
                    }else {
                        QueryWrapper<Servernode> serverQueryWrapper = new QueryWrapper<>();
                        serverQueryWrapper.eq("probeToken",token);
                        Servernode server = servernodeService.getOne(serverQueryWrapper);
                        if (!ObjectUtil.isNull(server)){
                            probeDataToMysql(vhostdataDao,message,server.getId());
                        }
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    /**
     * 发生错误时调用.
     * @param session Session
     * @param error   Throwable
     */
    @OnError
    public void onError(final Session session, final Throwable error) {
        log.info("连接发生错误:{}", error.getMessage());
        error.printStackTrace();
    }
}
