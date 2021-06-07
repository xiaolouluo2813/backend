package com.blockchain.server.eth.dto.wallet;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * EthWallet 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class EthWalletDTO extends BaseDTO {
    private String addr;
    private String tokenAddr;
    private String userOpenId;
    private String tokenSymbol;
    private int tokenDecimals;
    private BigDecimal balance;
    private BigDecimal freeBalance;
    private BigDecimal freezeBalance;
    private String walletType;
    private java.util.Date createTime;
    private java.util.Date updateTime;
}