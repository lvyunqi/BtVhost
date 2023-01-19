package com.chuqiyun.btvhost.probe;

import com.alibaba.fastjson2.JSONObject;
import com.chuqiyun.btvhost.dao.VhostdataDao;
import com.chuqiyun.btvhost.entity.Vhostdata;

import java.text.ParseException;

import static com.chuqiyun.btvhost.utils.LogUtil.logTimeConvert;

/**
 * @author mryunqi
 * @date 2023/1/17
 */
public class ProbeService {
    public static void probeDataToMysql(VhostdataDao vhostdataDao, String probeData, int serverId) throws ParseException {
        JSONObject jsonProbeData = JSONObject.parseObject(probeData);
        JSONObject event = jsonProbeData.getJSONObject("event");
        Vhostdata vhostdata = new Vhostdata();
        vhostdata.setVhostname(jsonProbeData.getString("user"));
        vhostdata.setRequestcode(event.getInteger("status"));
        vhostdata.setRequestsend(event.getLong("bodyBytesSent"));
        vhostdata.setRequestip(event.getString("remoteAddr"));
        vhostdata.setRequestdate(logTimeConvert(event.getString("timeLocal")));
        vhostdata.setRequest(event.getString("request"));
        vhostdata.setHttpreferer(event.getString("httpReferer"));
        vhostdata.setServerid(serverId);
        vhostdataDao.insert(vhostdata);
    }
    public static void cronProbeDataToMysql(VhostdataDao vhostdataDao, String probeData) throws ParseException {
        JSONObject jsonProbeData = JSONObject.parseObject(probeData);
        JSONObject event = jsonProbeData.getJSONObject("event");
        Vhostdata vhostdata = new Vhostdata();
        vhostdata.setVhostname(jsonProbeData.getString("user"));
        vhostdata.setRequestcode(event.getInteger("status"));
        vhostdata.setRequestsend(event.getLong("bodyBytesSent"));
        vhostdata.setRequestip(event.getString("remoteAddr"));
        vhostdata.setRequestdate(logTimeConvert(event.getString("timeLocal")));
        vhostdata.setRequest(event.getString("request"));
        vhostdata.setHttpreferer(event.getString("httpReferer"));
        vhostdata.setServerid(jsonProbeData.getInteger("serverID"));
        vhostdataDao.insert(vhostdata);
    }

}
