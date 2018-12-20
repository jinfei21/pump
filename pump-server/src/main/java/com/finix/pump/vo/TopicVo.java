package com.finix.pump.vo;

import lombok.Data;

@Data
public class TopicVo {
    private Integer id;
    private String topicName;
    private String department;
    private Long delayedThreshold;
    private Integer retentionDays;
    private String personInCharge;
    private String status;
    private String createTime;
    private Long appId;
    private String env;
}
