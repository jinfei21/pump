package com.finix.pump.vo;

import lombok.Data;

@Data
public class DBVo {
    private Long appId;
    private String env;
    private String host;
    private Integer port;
    private String dbName;
    private String userName;
    private String password;
    private String privs;
    private String dbType;
    private String status;
    private String approveMessage;
}
