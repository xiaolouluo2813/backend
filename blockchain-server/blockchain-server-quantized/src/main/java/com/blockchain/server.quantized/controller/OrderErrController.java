package com.blockchain.server.quantized.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.quantized.controller.api.OrderApi;
import com.blockchain.server.quantized.controller.api.TradingOnApi;
import com.blockchain.server.quantized.service.OrderErrService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Liusd
 * @create: 2019-04-20 10:26
 **/
@RestController
@Api(OrderApi.ORDER_API)
@RequestMapping("/orderErr")
public class OrderErrController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderErrController.class);

    @Autowired
    private OrderErrService orderErrService;

    @GetMapping("/list")
    @ApiOperation(value = TradingOnApi.List.METHOD_TITLE_NAME, notes = TradingOnApi.List.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(TradingOnApi.List.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                          @ApiParam(TradingOnApi.List.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(orderErrService.list());
    }
}
