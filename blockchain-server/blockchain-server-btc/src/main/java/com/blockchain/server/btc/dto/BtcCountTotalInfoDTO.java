package com.blockchain.server.btc.dto;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.server.btc.entity.BtcClearingCountDetail;
import com.blockchain.server.btc.entity.BtcClearingCountTotal;
import lombok.Data;

import java.util.List;

/**
 * EthWallet 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class BtcCountTotalInfoDTO extends BaseDTO {
    private BtcClearingCountTotal total;
    private List<BtcClearingCountDetail> details;
}