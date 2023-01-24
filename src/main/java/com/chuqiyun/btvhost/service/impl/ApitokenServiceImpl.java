package com.chuqiyun.btvhost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuqiyun.btvhost.dao.ApitokenDao;
import com.chuqiyun.btvhost.entity.Apitoken;
import com.chuqiyun.btvhost.service.ApitokenService;
import org.springframework.stereotype.Service;

/**
 * (Apitoken)表服务实现类
 *
 * @author mryunqi
 * @since 2023-01-24 20:25:33
 */
@Service("apitokenService")
public class ApitokenServiceImpl extends ServiceImpl<ApitokenDao, Apitoken> implements ApitokenService {

}

