package com.chuqiyun.btvhost.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (Sysconfig)表实体类
 *
 * @author mryunqi
 * @since 2023-01-19 21:20:26
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sysConfig")
@Data
public class Sysconfig extends Model<Sysconfig> {
    
    private Integer id;
    //redis监控数据持久化监控间隔
    private Integer probecronredistomysqltime;



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

