package com.finix.pump.vo;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class StargateVo {
    private Integer id;
    private Long appId;
    private String appName;
    private String orgName;
    private String env;
    private String domainName;
    private String personInCharge;
    private String appType;
    private String serviceType;
    private String grade;
    private Integer port;
    private String testers;
    private Set<String> clusterSet;
    private String status;
    private String approveMessage;
    private String currentCluster;
    private List<String> imageList;
    private String image;
    private List<String> releaseSpecificationList;
    private String releaseSpecification;
    private Integer instanceQuantity;
}
