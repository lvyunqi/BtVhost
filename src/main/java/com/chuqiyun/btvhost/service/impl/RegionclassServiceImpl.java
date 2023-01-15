package com.chuqiyun.btvhost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuqiyun.btvhost.dao.RegionclassDao;
import com.chuqiyun.btvhost.entity.Regionclass;
import com.chuqiyun.btvhost.service.RegionclassService;
import org.springframework.stereotype.Service;

/**
 * 地区分类(Regionclass)表服务实现类
 *
 * @author mryunqi
 * @since 2023-01-15 13:12:40
 */
@Service("regionclassService")
public class RegionclassServiceImpl extends ServiceImpl<RegionclassDao, Regionclass> implements RegionclassService {

}

