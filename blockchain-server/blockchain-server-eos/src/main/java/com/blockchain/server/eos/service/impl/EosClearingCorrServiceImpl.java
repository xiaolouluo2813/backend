package com.blockchain.server.eos.service.impl;


import com.blockchain.common.base.constant.WalletConstant;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.entity.EosClearingCorr;
import com.blockchain.server.eos.mapper.EosClearingCorrMapper;
import com.blockchain.server.eos.service.IEosClearingCorrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EosClearingCorrServiceImpl implements IEosClearingCorrService {
    @Autowired
    EosClearingCorrMapper eosClearingCorrMapper;

    @Override
    public List<EosClearingCorr> selectByTotalId(String totalId) {
        EosClearingCorr corr = new EosClearingCorr();
        corr.setTotalId(totalId);
        return eosClearingCorrMapper.select(corr);
    }

    @Override
    public void insert(EosClearingCorr eosClearingCorr) {
        SessionUserDTO user = SecurityUtils.getUser();
        eosClearingCorr.setSystemUserId( null != user ? user.getId(): WalletConstant.SAVE);
        eosClearingCorr.setId(UUID.randomUUID().toString());
        int row = eosClearingCorrMapper.insert(eosClearingCorr);
        if (row == 0) {
            throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }
}
