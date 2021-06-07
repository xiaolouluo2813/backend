package com.blockchain.server.quantized.dto;

import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Liusd
 * @create: 2019-04-23 11:04
 **/
@Data
public class OrderErrDto {

    private String id;
    private String symbol;
    private BigDecimal amount;
    private BigDecimal price;
    private String orderType;
    private String msg;
    private String userId;
    private Date createTime;
    private UserBaseInfoDTO userBaseInfoDTO;
}
