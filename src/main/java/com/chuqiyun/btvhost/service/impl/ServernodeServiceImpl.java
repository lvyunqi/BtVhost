package com.chuqiyun.btvhost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuqiyun.btvhost.dao.ServernodeDao;
import com.chuqiyun.btvhost.entity.Servernode;
import com.chuqiyun.btvhost.service.ServernodeService;
import org.springframework.stereotype.Service;

/**
 * (Servernode)表服务实现类
 *
 * @author mryunqi
 * @since 2023-01-17 20:04:41
 */
@Service("servernodeService")
public class ServernodeServiceImpl extends ServiceImpl<ServernodeDao, Servernode> implements ServernodeService {

}

