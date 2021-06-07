package com.blockchain.server.eth.common.util;

import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;

public class ParamsUtil {

    /**
     * 空判断后实例化
     *
     * @param paramsDTO
     * @return
     */
    public static WalletTxParamsDTO initParams(WalletTxParamsDTO paramsDTO) {
//        if (paramsDTO == null) paramsDTO = new WalletTxParamsDTO();
        return paramsDTO;
    }
}
