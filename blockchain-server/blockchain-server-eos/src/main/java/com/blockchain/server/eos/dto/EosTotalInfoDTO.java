package com.blockchain.server.eos.dto;

import com.blockchain.common.base.dto.BaseDTO;
import com.blockchain.server.eos.entity.EosClearingCorr;
import com.blockchain.server.eos.entity.EosClearingDetail;
import com.blockchain.server.eos.entity.EosClearingTotal;
import lombok.Data;

import java.util.List;

/**
 * EosTotalInfoDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class EosTotalInfoDTO extends BaseDTO {
    private EosClearingTotal total;
    private List<EosClearingCorr> corrs;
    private List<EosClearingDetail> details;
}