package com.blockchain.server.otc.dto.order;

import lombok.Data;

@Data
public class ListOrderParamDTO {
    private String userName;
    private String orderNumber;
    private String coinName;
    private String unitName;
    private String orderType;
    private String orderStatus;
    private String beginTime;
    private String endTime;
}
