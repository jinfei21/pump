package com.finix.pump.vo;

import lombok.Data;

@Data
public class StargateLogVo {
    private String id;
    private String clusterName;
    private String releaseGroupId;
    private String releaseGroupName;
    private String taskType;
    private String taskMessage;
    private String taskStaus;
    private String operator;
    private String operatorTime;
}
