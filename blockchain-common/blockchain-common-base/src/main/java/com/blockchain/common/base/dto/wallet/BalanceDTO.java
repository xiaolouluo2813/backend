package com.blockchain.common.base.dto.wallet;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDTO {
    BigDecimal balance; // 总额
    BigDecimal freeBalance; // 可用余额
    BigDecimal freezeBalance; // 冻结余额
}
