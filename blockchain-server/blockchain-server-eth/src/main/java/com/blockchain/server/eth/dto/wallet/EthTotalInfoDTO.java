package com.blockchain.server.eth.dto.wallet;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.server.eth.entity.EthClearingCorr;
import com.blockchain.server.eth.entity.EthClearingDetail;
import com.blockchain.server.eth.entity.EthClearingTotal;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * EthWallet 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class EthTotalInfoDTO extends BaseDTO {
    private EthClearingTotal total;
    private List<EthClearingCorr> corrs;
    private List<EthClearingDetail> details;
}