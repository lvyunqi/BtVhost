package com.chuqiyun.btvhost.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuqiyun.btvhost.constant.RedisConstant;
import com.chuqiyun.btvhost.dao.VhostdataDao;
import com.chuqiyun.btvhost.entity.Servernode;
import com.chuqiyun.btvhost.service.ServernodeService;
import com.chuqiyun.btvhost.service.WebSocketAsyncService;
import com.chuqiyun.btvhost.utils.ObjectUtil;
import com.chuqiyun.btvhost.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import static com.chuqiyun.btvhost.probe.ProbeService.probeDataToMysql;
import static com.chuqiyun.btvhost.utils.RedisUtil.isRedisConnected;

/**
 * @author mryunqi
 * @date 2023/1/17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WebSocketAsyncServiceImpl implements WebSocketAsyncService {
    private final VhostdataDao vhostdataDao;
    private final ServernodeService servernodeService;
    @Override
    @Async("taskExecutor")
    public void executeAsyncWs(String message, String token) {
        try {
            log.warn("start executeAsyncWs");
            if (isRedisConnected()){
                if (RedisUtil.exists(RedisConstant.PREFIX_VHOST_REFRESH_TOKEN + token)) {
                    int serverId = Integer.parseInt(Objects.requireNonNull(RedisUtil.get(RedisConstant.PREFIX_VHOST_REFRESH_TOKEN + token)));
                    JSONObject jsonMessage = JSONObject.parseObject(message);
                    jsonMessage.put("serverID",serverId);
                    RedisUtil.set(RedisConstant.PREFIX_VHOST_PROBE_DATA+serverId, jsonMessage.toJSONString());
                }else{
                    QueryWrapper<Servernode> serverQueryWrapper = new QueryWrapper<>();
                    serverQueryWrapper.eq("probeToken",token);
                    Servernode server = servernodeService.getOne(serverQueryWrapper);
                    if (!ObjectUtil.isNull(server)){
                        RedisUtil.set(RedisConstant.PREFIX_VHOST_REFRESH_TOKEN + token, String.valueOf(server.getId()));
                        JSONObject jsonMessage = JSONObject.parseObject(message);
                        jsonMessage.put("serverID",server.getId());
                        RedisUtil.set(RedisConstant.PREFIX_VHOST_PROBE_DATA+server.getId(), jsonMessage.toJSONString());
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
            log.warn("end executeAsyncWs");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
