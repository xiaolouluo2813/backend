package com.blockchain.server.cct.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("dapp-btc-server")
public interface BTCFeign {
    //请求路径
    String CONTENT_PATH = "/inner/walletTx";

    /***
     * 冻结余额
     * @param orderDTO
     * @return
     */
    @PostMapping(CONTENT_PATH + "/order")
    ResultDTO order(WalletOrderDTO orderDTO);

    /***
     * 扣减、增加总余额
     * @param changeDTO
     * @return
     */
    @PostMapping(CONTENT_PATH + "/change")
    ResultDTO change(WalletChangeDTO changeDTO);
}
