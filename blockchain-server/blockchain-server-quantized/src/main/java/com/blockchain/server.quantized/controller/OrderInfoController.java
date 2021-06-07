package com.blockchain.server.quantized.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.quantized.controller.api.OrderApi;
import com.blockchain.server.quantized.controller.api.TradingOnApi;
import com.blockchain.server.quantized.service.QuantizedOrderInfoService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: Liusd
 * @create: 2019-04-19 15:54
 **/

@RestController
@Api(OrderApi.ORDER_API)
@RequestMapping("/orderInfo")
public class OrderInfoController extends BaseController {

    @Autowired
    private QuantizedOrderInfoService quantizedOrderInfoService;

    /**
    * @Description:  列表
    * @Param: [pageNum, pageSize]
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/4/20
    */
    @GetMapping("/list")
    @ApiOperation(value = TradingOnApi.List.METHOD_TITLE_NAME, notes = TradingOnApi.List.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(TradingOnApi.List.METHOD_API_STATE) @RequestParam(value = "cctId",required = false) String cctId,@ApiParam(TradingOnApi.List.METHOD_API_TYPE) @RequestParam(value = "status",required = false) String status,@ApiParam(TradingOnApi.List.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                          @ApiParam(TradingOnApi.List.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(quantizedOrderInfoService.list(cctId,status));
    }

    /**
     * @Description: 继续处理
     * @Param: [symbol, orderId]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @ApiOperation(value = OrderApi.Cancel.METHOD_TITLE_NAME, notes = OrderApi.Cancel.METHOD_TITLE_NOTE)
    @PostMapping("/rehandle")
    public ResultDTO rehandle(@ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("id") String id) {
        quantizedOrderInfoService.rehandle(id);
        return ResultDTO.requstSuccess();
    }

}
