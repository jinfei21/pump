package com.finix.pump.vo;

import lombok.Data;

@Data
public class ContainerVo {
    private Integer id;
    private String instanceName;
    private String ip;
    private Integer port;
    private String runtimeStatus;
    private String containerId;
    private String containerStatus;
    private String hostIp;
}
