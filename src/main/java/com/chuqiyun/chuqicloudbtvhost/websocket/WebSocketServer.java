package com.chuqiyun.chuqicloudbtvhost.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mryunqi
 * @date 2023/1/7
 */
@Slf4j
@Component
@ServerEndpoint("/ws/vhost/{ipAddress}")
public class WebSocketServer {
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
    public void onOpen(final Session session, @PathParam("ipAddress") final String ipAddress) {
        INSTANCE_MAP.put(ipAddress, this);
        this.session = session;
        log.info("web连接成功  ipAddress:{}", ipAddress);
    }

    /**
     * 连接关闭调用的方法.
     */
    @OnClose
    public void onClose() {
        for (Map.Entry<String, WebSocketServer> entry : INSTANCE_MAP.entrySet()) {
            if (Objects.equals(entry.getValue(), this)) {
                log.info("web连接关闭, ipAddress:{}", entry.getKey());
                INSTANCE_MAP.remove(entry.getKey());
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法.
     * @param session Session
     * @param message 客户端发送过来的消息
     * @param ipAddress 用户唯一id
     */
    @OnMessage
    public void onMessage(final Session session, final String message, final @PathParam("ipAddress") String ipAddress) {
        log.info("接收客户端消息message:{}, ipAddress:{}", message, ipAddress);
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
