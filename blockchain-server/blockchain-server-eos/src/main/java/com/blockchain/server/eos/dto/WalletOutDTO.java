package com.blockchain.server.eos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Liusd
 * @create: 2019-03-26 17:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletOutDTO {

    private String id;
    private String addr;
    private String tokenSymbol;
    private String remark;
}
