package com.blockchain.server.sysconf.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

@Data
public class ProjectCenterDto extends BaseModel {
    private String id;
    private String currencyName;
    private String status;
    private Integer orderBy;
    private String issueTime;
    private String totalSupply;
    private String totalCirculation;
    private String icoAmount;
    private String whitePaper;
    private String coinUrl;
    private String presentation;
    private String descr;
    private String type;
    private String languages;
    private String classifyId;
    private String classifyName;
    private String uccn;
    private int starNum;
    private String createTime;

}