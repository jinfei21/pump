package com.finix.pump.vo;

import lombok.Data;

@Data
public class ApolloVo {
    private Long appId;
    private String appName;
    private String env;
    private String status;
    private String approveMessage;
}
