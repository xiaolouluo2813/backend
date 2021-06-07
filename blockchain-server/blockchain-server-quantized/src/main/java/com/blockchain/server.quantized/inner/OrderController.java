package com.blockchain.server.quantized.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.quantized.inner.api.OrderApi;
import com.blockchain.server.quantized.service.OrderService;
import com.blockchain.server.quantized.service.TradingOnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Harvey
 * @date 2019/3/9 11:57
 * @user WIN10
 */
@RestController
@Api(OrderApi.ORDER_API)
@RequestMapping("/inner/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TradingOnService tradingOnService;

    /**
    * @Description: 撤单
    * @Param: [symbol, orderId]
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/4/18
    */
    @ApiOperation(value = OrderApi.Cancel.METHOD_TITLE_NAME, notes = OrderApi.Cancel.METHOD_TITLE_NOTE)
    @PostMapping("/cancellations")
    public ResultDTO cancellations(@ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("orderId") String orderId) {
        return ResultDTO.requstSuccess( orderService.cancel(orderId));
    }

    /**
    * @Description: 查询交易对
    * @Param: [coinName, unitName]
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/4/25
    */
    @ApiOperation(value = OrderApi.Cancel.METHOD_TITLE_NAME, notes = OrderApi.Cancel.METHOD_TITLE_NOTE)
    @PostMapping("/getTradingOnByCoinNameAndUnitName")
    public ResultDTO getTradingOn(@ApiParam(OrderApi.Cancel.SYMBOL) @RequestParam("coinName") String coinName, @ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("unitName") String unitName) {
        return ResultDTO.requstSuccess(tradingOnService.selectOne(coinName,unitName));
    }
}
