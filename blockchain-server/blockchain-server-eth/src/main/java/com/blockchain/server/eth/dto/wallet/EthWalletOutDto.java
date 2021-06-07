package com.blockchain.server.eth.dto.wallet;

import lombok.Data;

/**
 * @author: Liusd
 * @create: 2019-03-27 09:32
 **/
@Data
public class EthWalletOutDto {
    private String id;
    private String addr;
    private String tokenSymbol;
    private Integer tokenDecimals;
    private String remark;
}
