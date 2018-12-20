package com.finix.pump.vo;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

@Data
public class CacheVo {
    private Integer id;
    private Long appId;
    private Long cacheId;
    private String name;
    private String appDescription;
    private String storeType;
    private String memory;
    private String personInCharge;
    private String test;
    private String dataSource;
    private String persistent;
    private String hotBackup;
    private Integer qps;
    private Integer termQuantity;
    private String generatorRoom;
    private Integer memoryUsedWarningThreshold;
    private Integer connectionQuantityWarningThreshold;
    private String usedMemory;
    private Integer hitRate;
    private Integer runtime;
    private String status;
    private String env;
}
