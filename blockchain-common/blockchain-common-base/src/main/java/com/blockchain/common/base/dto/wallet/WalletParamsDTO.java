package com.blockchain.common.base.dto.wallet;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;

/**
 * 钱包信息，查询条件DTO
 */
@Data
public class WalletParamsDTO extends BaseDTO {
    String userId;  // 用户ID
    String userName; // 账户
    String coinName; // 币种名称 (例如 币种地址，币种ID等)
    String coinType; // 币种标识 (例如 币种地址，币种ID等)
    String walletType;
    String startDate; // 开始时间
    String endDate; // 结束时间
}
