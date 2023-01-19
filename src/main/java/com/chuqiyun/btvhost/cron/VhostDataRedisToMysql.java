package com.chuqiyun.btvhost.cron;

import com.chuqiyun.btvhost.dao.VhostdataDao;
import com.chuqiyun.btvhost.service.SysconfigService;
import com.chuqiyun.btvhost.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashMap;

import static com.chuqiyun.btvhost.constant.RedisConstant.PREFIX_VHOST_PROBE_DATA;
import static com.chuqiyun.btvhost.probe.ProbeService.cronProbeDataToMysql;
import static com.chuqiyun.btvhost.utils.RedisUtil.getHashMap;
import static com.chuqiyun.btvhost.utils.RedisUtil.isRedisConnected;

/**
 * @author mryunqi
 * @date 2023/1/19
 */
@Component
@EnableScheduling
@RequiredArgsConstructor
public class VhostDataRedisToMysql {
    private final VhostdataDao vhostdataDao;

    @Async("taskExecutor")
    @Scheduled(fixedDelay = 120000) //2分钟
    public void vhostDataRedisToMysql(){
        if(isRedisConnected()){
            HashMap<String, String> map = getHashMap(PREFIX_VHOST_PROBE_DATA);
            if (map == null || map.size() == 0) {
                return;
            }
            int mapSize = 1000;
            int count = 0;
            if (map.size() < mapSize){
                mapSize = map.size();
            }
            for (String key : map.keySet()){
                if (count >=mapSize){
                    break;
                }
                String value = map.get(key);
                try {
                    cronProbeDataToMysql(vhostdataDao,value);
                } catch (ParseException e) {
                    count++;
                    continue;
                }
                RedisUtil.delete(key);
                count++;
            }
        }
    }
}
