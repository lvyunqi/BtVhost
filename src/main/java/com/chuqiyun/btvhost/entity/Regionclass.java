package com.chuqiyun.btvhost.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 地区分类(Regionclass)表实体类
 *
 * @author mryunqi
 * @since 2023-01-15 13:12:40
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "regionClass")
@Data
public class Regionclass extends Model<Regionclass> {

    private Integer id;
    private String firstclass;
    
    private String parentclass;
    
    private String nodelist;
    //1=顺序，2=自动
    private Integer scheduling;


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

