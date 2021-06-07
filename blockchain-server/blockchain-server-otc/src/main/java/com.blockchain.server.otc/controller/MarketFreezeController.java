package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.MarketFreezeApi;
import com.blockchain.server.otc.dto.marketfreeze.ListMarketFreezeResultDTO;
import com.blockchain.server.otc.service.MarketFreezeService;
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

@Api(MarketFreezeApi.MARKET_FREEZE_API)
@RestController
@RequestMapping("/marketFreeze")
public class MarketFreezeController extends BaseController {

    @Autowired
    private MarketFreezeService marketFreezeService;

    @ApiOperation(value = MarketFreezeApi.list.METHOD_TITLE_NAME,
            notes = MarketFreezeApi.list.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO list(@ApiParam(MarketFreezeApi.list.METHOD_API_USER_NAME) @RequestParam(value = "userName", required = false) String userName,
                          @ApiParam(MarketFreezeApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                          @ApiParam(MarketFreezeApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListMarketFreezeResultDTO> list = marketFreezeService.list(userName);
        return generatePage(list);
    }
}
