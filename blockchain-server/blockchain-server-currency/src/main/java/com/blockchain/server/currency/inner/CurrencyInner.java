package com.blockchain.server.currency.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.currency.service.CurrencyPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inner/currencyPair")
public class CurrencyInner {

    @Autowired
    private CurrencyPairService currencyPairService;

    /***
     * 更新行情币对
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    @PostMapping("/updateCurrencyPair")
    public ResultDTO updateCurrencyPair(@RequestParam("coinName") String coinName,
                                        @RequestParam("unitName") String unitName,
                                        @RequestParam("status") String status) {
        return ResultDTO.requstSuccess(currencyPairService.updateCurrencyPaifOfCct(coinName, unitName, status));
    }
}
