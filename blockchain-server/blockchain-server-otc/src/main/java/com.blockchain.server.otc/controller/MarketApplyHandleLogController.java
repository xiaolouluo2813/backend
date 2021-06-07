package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.MarketApplyHandleLogApi;
import com.blockchain.server.otc.dto.marketapplyhandlelog.ListMarketApplyHandleLogResultDTO;
import com.blockchain.server.otc.service.MarketApplyHandleLogService;
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

@Api(MarketApplyHandleLogApi.MARKET_APPLY_HANDLE_LOG_API)
@RestController
@RequestMapping("/marketApplyLog")
public class MarketApplyHandleLogController extends BaseController {

    @Autowired
    private MarketApplyHandleLogService handleLogService;

    @ApiOperation(value = MarketApplyHandleLogApi.list.METHOD_TITLE_NAME,
            notes = MarketApplyHandleLogApi.list.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO list(@ApiParam(MarketApplyHandleLogApi.list.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                          @ApiParam(MarketApplyHandleLogApi.list.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                          @ApiParam(MarketApplyHandleLogApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                          @ApiParam(MarketApplyHandleLogApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListMarketApplyHandleLogResultDTO> list = handleLogService.list(beginTime, endTime);
        return generatePage(list);
    }
}
