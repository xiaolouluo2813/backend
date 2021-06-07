package com.blockchain.server.currency.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dapp-cct-server", path = "/inner")
public interface CctFeign {

    /***
     * 更新币币模块的币种
     * @param coinName
     * @param unitName
     * @param status
     * @param sysUserId
     * @param ipAddr
     * @return
     */
    @PostMapping("/coin/updateCoin")
    ResultDTO updateCctCoin(@RequestParam("coinName") String coinName,
                            @RequestParam("unitName") String unitName,
                            @RequestParam("status") String status,
                            @RequestParam("sysUserId") String sysUserId,
                            @RequestParam("ipAddr") String ipAddr);
}
