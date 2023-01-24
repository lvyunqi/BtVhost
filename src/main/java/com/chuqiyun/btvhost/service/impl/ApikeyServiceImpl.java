package com.chuqiyun.btvhost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuqiyun.btvhost.dao.ApikeyDao;
import com.chuqiyun.btvhost.entity.Apikey;
import com.chuqiyun.btvhost.service.ApikeyService;
import org.springframework.stereotype.Service;

/**
 * (Apikey)表服务实现类
 *
 * @author mryunqi
 * @since 2023-01-24 18:48:11
 */
@Service("apikeyService")
public class ApikeyServiceImpl extends ServiceImpl<ApikeyDao, Apikey> implements ApikeyService {

}

