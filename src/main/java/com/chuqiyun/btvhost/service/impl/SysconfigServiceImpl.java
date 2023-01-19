package com.chuqiyun.btvhost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuqiyun.btvhost.dao.SysconfigDao;
import com.chuqiyun.btvhost.entity.Sysconfig;
import com.chuqiyun.btvhost.service.SysconfigService;
import org.springframework.stereotype.Service;

/**
 * (Sysconfig)表服务实现类
 *
 * @author mryunqi
 * @since 2023-01-19 21:20:26
 */
@Service("sysconfigService")
public class SysconfigServiceImpl extends ServiceImpl<SysconfigDao, Sysconfig> implements SysconfigService {

}

