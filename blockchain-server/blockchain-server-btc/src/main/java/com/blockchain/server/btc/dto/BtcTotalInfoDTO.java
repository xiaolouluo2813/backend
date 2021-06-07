package com.blockchain.server.btc.dto;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.server.btc.entity.BtcClearingCorr;
import com.blockchain.server.btc.entity.BtcClearingDetail;
import com.blockchain.server.btc.entity.BtcClearingTotal;
import lombok.Data;

import java.util.List;

/**
 * EthWallet 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class BtcTotalInfoDTO extends BaseDTO {
    private BtcClearingTotal total;
    private List<BtcClearingCorr> corrs;
    private List<BtcClearingDetail> details;
}