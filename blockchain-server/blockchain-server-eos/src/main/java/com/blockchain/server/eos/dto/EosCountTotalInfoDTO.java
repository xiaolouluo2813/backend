package com.blockchain.server.eos.dto;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.server.eos.entity.EosClearingCountDetail;
import com.blockchain.server.eos.entity.EosClearingCountTotal;
import lombok.Data;

import java.util.List;

/**
 * EthWallet 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class EosCountTotalInfoDTO extends BaseDTO {
    private EosClearingCountTotal total;
    private List<EosClearingCountDetail> details;
}