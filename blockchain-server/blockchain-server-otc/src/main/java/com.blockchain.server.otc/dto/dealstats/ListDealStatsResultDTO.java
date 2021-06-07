package com.blockchain.server.otc.dto.dealstats;

import lombok.Data;

@Data
public class ListDealStatsResultDTO {
    private String userId;
    private String userName;
    private String realName;
    private String nickName;
    private Integer adTransNum;
    private Integer adMarkNum;
    private Integer orderSellNum;
    private Integer orderBuyNum;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
