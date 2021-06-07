package com.blockchain.server.cct.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dapp-currency-server", path = "/inner")
public interface CurrencyFeign {

    /***
     * 更新行情币对
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    @PostMapping("/currencyPair/updateCurrencyPair")
    ResultDTO updateCurrencyPair(@RequestParam("coinName") String coinName,
                                 @RequestParam("unitName") String unitName,
                                 @RequestParam("status") String status);
}
