package com.finix.pump.po;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangxiao04
 * @date 2018/04/25
 */
@Data
public class EventLogEntity {

    private Long id;
    private String eventName;
    private String eventAddition;
    private String optionType;
    private String operator;
    private Date operateTime;

}
