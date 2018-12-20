package com.finix.pump.vo;

import lombok.Data;

@Data
public class ReleaseGroupVo {
    private Long id;
    private String releaseGroupName;
    private String version;
    private Integer port;
    private String image;
    private String env;
    private String clusterName;
    //容器数量
    private Integer containerQuantity;
    //当前流量
    private Integer runningContainerQuantity;
    //配额
    private String releaseSpecification;
}
