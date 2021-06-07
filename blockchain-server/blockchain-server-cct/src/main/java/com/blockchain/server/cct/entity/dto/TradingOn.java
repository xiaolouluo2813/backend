package com.blockchain.server.cct.entity.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * AppCctTradingRecord 数据传输类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Data
public class TradingOn extends BaseModel {
    private String id;
    private String coinName;
    private String unitName;
    private String state;
    private Date createTime;
}