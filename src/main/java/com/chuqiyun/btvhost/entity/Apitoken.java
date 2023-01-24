package com.chuqiyun.btvhost.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (Apitoken)表实体类
 *
 * @author mryunqi
 * @since 2023-01-24 20:25:33
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "apiToken")
@Data
public class Apitoken extends Model<Apitoken> {
    @TableId("appId")
    private Integer appid;
    
    private String token;
    
    private Long endtime;


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

