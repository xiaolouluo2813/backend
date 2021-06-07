package com.blockchain.common.base.dto.wallet;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包提现记录信息，查询条件DTO
 */
@Data
public class WalletTxParamsDTO extends BaseDTO {
    String txId; // 记录ID
    String hash;
    String from; // 支付
    String to; // 接收
    String userId;  // 用户ID
    String userName; // 账户
    String coinName; // 币种名称 (例如 币种地址，币种ID等)
    String coinType; // 币种标识 (例如 币种地址，币种ID等)
    String txType; // 转账类型
    Integer txStatus; // 转账状态
    String startDate; // 开始时间
    String endDate; // 结束时间
}
