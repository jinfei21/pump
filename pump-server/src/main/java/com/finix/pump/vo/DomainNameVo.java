package com.finix.pump.vo;

import lombok.Data;

@Data
public class DomainNameVo {
    private Long appId;
    private String domainName;
    private String status;
    private String env;
    private String approveMessage;
}
