package com.chuqiyun.btvhost.entity;

import lombok.Data;

import java.util.List;

/**
 * @author mryunqi
 * @date 2023/1/15
 */
@Data
public class RegionClassList {
    private String parentClass;
    private List<Regions> regions;
    private String code;
}
