package com.blockchain.server.btc.dto;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * EthWallet 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class BtcWalletBillDTO extends BaseDTO {
    Set<String> addrs; // 地址集合
    Date startDate;  // 期初时间
    Date endDate;   // 期末时间
    Date maxDate;  // 最大更新时间
    Date minDate; // 最小更新时间
}