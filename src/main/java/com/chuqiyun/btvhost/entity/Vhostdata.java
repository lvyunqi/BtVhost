package com.chuqiyun.btvhost.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (Vhostdata)表实体类
 *
 * @author mryunqi
 * @since 2023-01-17 22:29:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "vhostData")
public class Vhostdata extends Model<Vhostdata> {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //虚拟主机名
    private String vhostname;
    //请求状态码
    private Integer requestcode;
    //请求流量
    private Long requestsend;
    //请求IP
    private String requestip;
    
    private Date requestdate;
    //请求
    private String request;
    //请求来源
    private String httpreferer;
    private Integer serverid;



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

