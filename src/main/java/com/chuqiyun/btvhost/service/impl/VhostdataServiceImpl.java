package com.chuqiyun.btvhost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuqiyun.btvhost.dao.VhostdataDao;
import com.chuqiyun.btvhost.entity.Vhostdata;
import com.chuqiyun.btvhost.service.VhostdataService;
import org.springframework.stereotype.Service;

/**
 * (Vhostdata)表服务实现类
 *
 * @author mryunqi
 * @since 2023-01-17 22:29:46
 */
@Service("vhostdataService")
public class VhostdataServiceImpl extends ServiceImpl<VhostdataDao, Vhostdata> implements VhostdataService {

}

