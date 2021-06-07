package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.CoinHandleLogApi;
import com.blockchain.server.otc.dto.coinhandlelog.ListCoinHandleLogResultDTO;
import com.blockchain.server.otc.service.CoinHandleLogService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(CoinHandleLogApi.COIN_HANDLE_LOG_API)
@RestController
@RequestMapping("/coinHandleLog")
public class CoinHandleLogController extends BaseController {

    @Autowired
    private CoinHandleLogService coinHandleLogService;

    @ApiOperation(value = CoinHandleLogApi.listCoinHandleLog.METHOD_TITLE_NAME,
            notes = CoinHandleLogApi.listCoinHandleLog.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listCoinHandleLog(@ApiParam(CoinHandleLogApi.listCoinHandleLog.METHOD_API_COIN_ID) @RequestParam(value = "coinId", required = false) String coinId,
                                       @ApiParam(CoinHandleLogApi.listCoinHandleLog.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                                       @ApiParam(CoinHandleLogApi.listCoinHandleLog.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                       @ApiParam(CoinHandleLogApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @ApiParam(CoinHandleLogApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListCoinHandleLogResultDTO> resultDTOS = coinHandleLogService.listCoinHandleLog(coinId, beginTime, endTime);
        return generatePage(resultDTOS);
    }
}
