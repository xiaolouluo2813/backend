package com.blockchain.server.eth.dto.web3j;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;

@Data
public class Web3jWalletDTO extends BaseDTO {
    private String addr;
    private String privateKey;
    private String keystore;
}
