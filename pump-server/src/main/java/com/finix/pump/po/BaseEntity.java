package com.finix.pump.po;

import lombok.Data;

import java.util.Date;


/**
 * @author zhangxiao04
 * @date 2018/04/25
 */
@Data
public class BaseEntity {

    private String insertBy;
    private String updateBy;
    private Date insertTime;
    private Date updateTime;
    private Boolean isActive;
}
