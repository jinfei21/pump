package com.finix.pump.vo;

import lombok.Data;

@Data
public class CustomerGroupVo {
    private Integer id;
    private String consumerGroupName;
    private Integer quantity;
    private String topicName;
    private String applicant;
    private String createTime;
    private String status;
    private Long appId;
    private String env;
}
