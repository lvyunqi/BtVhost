package com.chuqiyun.authorization.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mryunqi
 * @date 2023/1/9
 */
@Data
public class LicenseCheckModel implements Serializable {


    private static final long serialVersionUID = -501973840917143108L;
    /**
     * 可被允许的IP地址
     */
    private List<String> ipAddress;

    /**
     * 可被允许的MAC地址
     */
    private List<String> macAddress;

    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;
}
