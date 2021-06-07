package com.blockchain.server.quantized.dto;

import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import lombok.Data;

/**
 * @author: Liusd
 * @create: 2019-04-23 10:05
 **/
@Data
public class QuantizedOrderDto {

    private long id;
    private String amount;
    private long canceledAt;
    private long createdAt;
    private String fieldAmount;
    private String fieldCashAmount;
    private String fieldFees;
    private long finishedAt;
    private String price;
    private String source;
    private String state;
    private String symbol;
    private String type;
    private String userId;
    private UserBaseInfoDTO userBaseInfoDTO;
}
