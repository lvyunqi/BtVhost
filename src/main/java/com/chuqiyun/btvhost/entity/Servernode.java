package com.chuqiyun.btvhost.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (Servernode)表实体类
 *
 * @author mryunqi
 * @since 2023-01-17 20:04:41
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "serverNode")
@Data
public class Servernode extends Model<Servernode> {
    
    private Integer id;
    //节点地址
    private String host;
    //端口
    private Integer port;
    //服务器接口密钥
    private String skey;
    //探针token
    private String probetoken;
    //服务器操作系统 默认0=Linux 1=Windows
    private Integer os;
    //环境架构 默认0=LNMP 1=LAMP 2=IIS 3=LNP 4=LAP
    private Integer soft;
    //服务器是否开启SSL 默认0=关闭 1=开启
    private Integer serverssl;
    //区域所属
    private Integer groupclass;
    //状态 默认0=开启 1=关闭
    private Integer satus;



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

