package com.blockchain.server.eos.dto;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * EthWalletTxBillDTO 流水账单
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class EosWalletTxBillDTO extends BaseDTO {
    Map<String, Map<String, BigDecimal>> fromMap; // 作为支付方的流水
    Map<String, Map<String, BigDecimal>> toMap; // 作为收款方的流水
    BigDecimal countFromAmount; // from的累计金额
    BigDecimal countToAmount; // to的累计金额
    BigDecimal countAmount; // 累计金额
}