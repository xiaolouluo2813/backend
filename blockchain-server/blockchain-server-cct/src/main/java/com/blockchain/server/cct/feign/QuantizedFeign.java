package com.blockchain.server.cct.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.cct.entity.dto.TradingOn;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangxl
 * @create 2019-04-25 17:13
 */
@FeignClient("dapp-quantized-server")
public interface QuantizedFeign {
    /**
     * @Description: 撤单
     * @Param: [symbol, orderId]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @PostMapping("/inner/order/cancellations")
    ResultDTO<String> cancellations(@RequestParam("orderId") String orderId);

    /**
     * @Description: 查询交易对
     * @Param: [coinName, unitName]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/25
     */
    @PostMapping("/inner/order/getTradingOnByCoinNameAndUnitName")
    ResultDTO<TradingOn> getTradingOn(@RequestParam("coinName") String coinName, @RequestParam("unitName") String unitName);
}
