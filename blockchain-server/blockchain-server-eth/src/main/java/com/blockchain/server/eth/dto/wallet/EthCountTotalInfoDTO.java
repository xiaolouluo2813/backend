package com.blockchain.server.eth.dto.wallet;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.server.eth.entity.*;
import lombok.Data;

import java.util.List;

/**
 * EthWallet 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class EthCountTotalInfoDTO extends BaseDTO {
    private EthClearingCountTotal total;
    private List<EthClearingCountDetail> details;
}