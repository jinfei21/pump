package com.finix.pump.vo;

import lombok.Data;

import java.util.List;

/**
 * 页面展示用
 *
 * @author zhangxiao04
 */
@Data
public class AppVO {
    private Long id;
    private Long appId;
    private String appName;
    private String orgName;
    private String grade;
    private String appType;
    private String serviceType;
    private String applyStatus;
    private String applyDate;
    private String approveDate;
    private ApolloVo apollo;
    private DomainNameVo domainName;
    private Integer releaseTimes;
    private List<CustomerGroupVo> customerList;
    private List<TopicVo> topicList;
    private List<CacheVo> cacheList;
    private DBVo db;
    private StargateVo stargate;
    private String approveMessage;
}
