package com.blockchain.common.base.dto.wallet;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 钱包提现记录信息DTO
 */
@Data
public class WalletTxDTO extends BaseDTO {
    UserBaseInfoDTO userBaseInfoDTO; // 用户信息
    String userId; // 用户ID
    String txId; // 记录ID
    String hash; // 打包标识
    String from; // 支付
    String to; // 收款
    String coinMain;  // 币种主体（例如 ETH , BTC , EOS）
    String coinName; // 币种名称
    String coinType; // 币种标识 (例如 币种地址，币种ID等)
    BigDecimal amount; // 转账金额
    BigDecimal relAmount; // 实际到账
    String gasType; // 手续费币种标识 (例如 币种地址，币种ID等)
    String gasName; // 手续费币种名称
    BigDecimal gasPrice; // 手续费
    String txType; // 转账类型
    Integer txStatus; // 转账状态
    String remark; // 转账备注
    String createTime; // 创建时间
    String updateTime; // 修改时间

}
