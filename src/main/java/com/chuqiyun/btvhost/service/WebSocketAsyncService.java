package com.chuqiyun.btvhost.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.CountDownLatch;

/**
 * @author mryunqi
 * @date 2023/1/17
 */
public interface WebSocketAsyncService {

    @Async("taskExecutor")
    void executeAsyncWs(String message,String token);
}
