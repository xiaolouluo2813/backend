package com.blockchain.common.base.dto.wallet;

import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 钱包信息DTO
 */
@Data
public class WalletBaseDTO {
    UserBaseInfoDTO userBaseInfoDTO;
    String addr;
    String hexAddr;
    Integer coinDecimals; // 币种小数位
    String coinMain;  // 币种主体（例如 ETH , BTC , EOS）
    String coinName; // 币种名称
    String coinType; // 币种标识 (例如 币种地址，币种ID等)
    String walletType; // 钱包类型
    String userId; // 用户ID
    String balance; // 总额
    String freeBalance; // 可用余额
    String freezeBalance; // 冻结余额
    java.util.Date createTime; // 创建时间
    java.util.Date updateTime; // 修改时间
}
