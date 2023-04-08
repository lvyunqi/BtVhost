package com.chuqiyun.btvhost.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 超管表(User)表实体类
 *
 * @author mryunqi
 * @since 2023-01-11 00:16:27
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
@Data
public class User extends Model<User> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //用户名
    private String username;
    //密码
    private String password;
    //邮箱
    private String email;
    //手机号
    private String mobil;
    //权限组
    private Integer permissiongroup;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
    }

