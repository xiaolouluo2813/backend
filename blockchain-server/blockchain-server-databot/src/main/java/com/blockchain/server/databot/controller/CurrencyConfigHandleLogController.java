package com.blockchain.server.databot.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.databot.controller.api.CurrencyConfigHandleLogApi;
import com.blockchain.server.databot.dto.currencyconfig.UpdateConfigParamDTO;
import com.blockchain.server.databot.dto.currencyconfighandlelog.ListConfigHandleLogResultDTO;
import com.blockchain.server.databot.service.CurrencyConfigHandleLogService;
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

@Api(CurrencyConfigHandleLogApi.CURRENCY_CONFIG_HANDLE_LOG_API)
@RestController
@RequestMapping("/configHandleLog")
public class CurrencyConfigHandleLogController extends BaseController {

    @Autowired
    private CurrencyConfigHandleLogService configHandleLogService;

    @ApiOperation(value = CurrencyConfigHandleLogApi.list.METHOD_TITLE_NAME,
            notes = CurrencyConfigHandleLogApi.list.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO list(@ApiParam(CurrencyConfigHandleLogApi.list.METHOD_API_CURRENCY_PAIR)
                          @RequestParam(value = "currencyPair", required = false) String currencyPair,
                          @ApiParam(CurrencyConfigHandleLogApi.list.METHOD_API_HANDLE_TYPE)
                          @RequestParam(value = "handleType", required = false) String handleType,
                          @ApiParam(CurrencyConfigHandleLogApi.list.METHOD_API_BEGIN_TIME)
                          @RequestParam(value = "beginTime", required = false) String beginTime,
                          @ApiParam(CurrencyConfigHandleLogApi.list.METHOD_API_END_TIME)
                          @RequestParam(value = "endTime", required = false) String endTime,
                          @ApiParam(CurrencyConfigHandleLogApi.list.METHOD_API_PAGENUM)
                          @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                          @ApiParam(CurrencyConfigHandleLogApi.list.METHOD_API_PAGESIZE)
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListConfigHandleLogResultDTO> resultDTOS = configHandleLogService.listConfigHandleLog(currencyPair, handleType, beginTime, endTime);
        return generatePage(resultDTOS);
    }

}
