package com.blockchain.server.cct.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.cct.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inner/coin")
public class CoinInner {

    @Autowired
    private CoinService coinService;

    /***
     * 更新币种
     * @param coinName
     * @param unitName
     * @param status
     * @param sysUserId
     * @param ipAddr
     * @return
     */
    @PostMapping("/updateCoin")
    public ResultDTO updateCoin(@RequestParam("coinName") String coinName,
                                @RequestParam("unitName") String unitName,
                                @RequestParam("status") String status,
                                @RequestParam("sysUserId") String sysUserId,
                                @RequestParam("ipAddr") String ipAddr) {
        return ResultDTO.requstSuccess(coinService.updateCoinOfCurrency(coinName, unitName, status, sysUserId, ipAddr));
    }
}
