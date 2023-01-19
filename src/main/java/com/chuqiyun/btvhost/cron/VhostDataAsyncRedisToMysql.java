package com.chuqiyun.btvhost.cron;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.HashMap;

import static com.chuqiyun.btvhost.constant.RedisConstant.PREFIX_VHOST_PROBE_DATA;
import static com.chuqiyun.btvhost.utils.RedisUtil.getHashMap;
import static com.chuqiyun.btvhost.utils.RedisUtil.isRedisConnected;

/**
 * @author mryunqi
 * @date 2023/1/17
 */
@Slf4j
@Component
public class VhostDataAsyncRedisToMysql implements ApplicationRunner {
    @Resource(name = "taskExecutor")
    private TaskExecutor executor;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
