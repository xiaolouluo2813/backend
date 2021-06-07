package com.blockchain.server.quantized.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.quantized.controller.api.TradingOnApi;
import com.blockchain.server.quantized.service.CoinService;
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
 * @create: 2019-04-18 15:54
 **/
@RestController
@RequestMapping("/coin")
@Api(TradingOnApi.CONTROLLER_API)
public class CoinController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CoinController.class);

    @Autowired
    private CoinService coinService;

    /**
    * @Description: 列表
    * @Param: [pageNum, pageSize]
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/4/18
    */
    @GetMapping("/list")
    @ApiOperation(value = TradingOnApi.List.METHOD_TITLE_NAME, notes = TradingOnApi.List.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(TradingOnApi.List.METHOD_API_PAGENUM) @RequestParam("type") String type) {
        return generatePage(coinService.list(type));
    }

}
