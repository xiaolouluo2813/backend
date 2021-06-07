package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.MarketUserHandleLogApi;
import com.blockchain.server.otc.dto.marketuserhandlelog.ListMarketUserHandleLogResultDTO;
import com.blockchain.server.otc.service.MarketUserHandleLogService;
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

@Api(MarketUserHandleLogApi.MARKET_USER_HANDLE_LOG_API)
@RestController
@RequestMapping("/marketUserLog")
public class MarketUserHandleLogController extends BaseController {

    @Autowired
    private MarketUserHandleLogService handleLogService;

    @ApiOperation(value = MarketUserHandleLogApi.list.METHOD_TITLE_NAME,
            notes = MarketUserHandleLogApi.list.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO list(@ApiParam(MarketUserHandleLogApi.list.METHOD_API_USER_NAME) @RequestParam(value = "userName", required = false) String userName,
                          @ApiParam(MarketUserHandleLogApi.list.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                          @ApiParam(MarketUserHandleLogApi.list.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                          @ApiParam(MarketUserHandleLogApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                          @ApiParam(MarketUserHandleLogApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListMarketUserHandleLogResultDTO> list = handleLogService.list(userName, beginTime, endTime);
        return generatePage(list);
    }
}
