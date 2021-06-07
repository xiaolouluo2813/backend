package com.blockchain.common.base.dto.wallet;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * WalletCountTotalDTO 资金平衡统计的基本数据
 */
@Data
public class WalletCountTotalDTO extends BaseDTO {
    String addr; // 钱包地址
    String coinName; // 币种类型
    String userId; // 用户ID
    String tokenDecimal; // 小数位
    String balance; // 总额
    String freeBalance; // 可用余额
    String freezeBalance; // 冻结余额
    Date walletCreateTime; // 创建时间
    Date walletUpdateTime; // 修改时间
    String txId;
    String fromAddr;
    String toAddr;
    BigDecimal amount;
    String tokenAddr;
    String tokenSymbol;
    String transferType;
    Integer status;
    java.util.Date createTime;
    java.util.Date updateTime;

}
