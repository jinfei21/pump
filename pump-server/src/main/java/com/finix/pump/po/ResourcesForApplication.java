package com.finix.pump.po;

import lombok.Data;

@Data
public class ResourcesForApplication extends BaseEntity{
    private Long id;
    private Byte type;
    private String name;
    private String content;
    private String result;
    private Long appId;
}
