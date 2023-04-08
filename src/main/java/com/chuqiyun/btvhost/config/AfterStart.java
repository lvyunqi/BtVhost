package com.chuqiyun.btvhost.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chuqiyun.btvhost.dao.UserDao;
import com.chuqiyun.btvhost.entity.User;
import com.chuqiyun.btvhost.utils.EncryptUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author mryunqi
 * @date 2023/4/8
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AfterStart {

    private final UserDao userDao;

    /**
    * @Author: mryunqi
    * @Description: 启动时执行
    * @DateTime: 2023/4/8 13:55
    */

    @PostConstruct
    public void run(){
        QueryWrapper<User> userList = new QueryWrapper<>();
        userList.ge("id",0);
        Long count = userDao.selectCount(userList);
        if (count == 0){
            log.info("开始创建初始[超级管理员]账号");
            User user = new User();
            user.setUsername("admin");
            user.setMobil("admin");
            user.setEmail("admin");
            user.setPassword(EncryptUtil.md5("123456"));
            userDao.insert(user);
            log.info("创建成功！初始username：admin 初始password：123456");
        }
    }

    /**
    * @Author: mryunqi
    * @Description: 关闭后执行
    * @DateTime: 2023/4/8 13:57
    */
    @PreDestroy
    public void stop(){
    }

}
