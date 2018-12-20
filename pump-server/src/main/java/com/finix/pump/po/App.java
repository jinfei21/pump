package com.finix.pump.po;

import lombok.Data;

@Data
public class App extends BaseEntity {
    private Long id;
    private Long appId;
    private String content;
    private String resources;
    private Byte status;
    private Long ownerId;
}
