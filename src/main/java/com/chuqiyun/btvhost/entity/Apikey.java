package com.chuqiyun.btvhost.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (Apikey)表实体类
 *
 * @author mryunqi
 * @since 2023-01-24 18:48:11
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "apiKey")
@Data
public class Apikey extends Model<Apikey> {
    @TableId("appId")
    private Integer appid;
    private String apikey;
    private String secretkey;
    private Date createdate;



    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.appid;
    }
    }

