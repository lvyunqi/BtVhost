package com.chuqiyun.chuqicloudbtvhost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuqiyun.chuqicloudbtvhost.dao.UserDao;
import com.chuqiyun.chuqicloudbtvhost.entity.User;
import com.chuqiyun.chuqicloudbtvhost.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 超管表(User)表服务实现类
 *
 * @author mryunqi
 * @since 2023-01-11 00:16:32
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

