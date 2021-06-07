package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.DealStatsApi;
import com.blockchain.server.otc.dto.dealstats.ListDealStatsResultDTO;
import com.blockchain.server.otc.service.DealStatsService;
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

@Api(DealStatsApi.DEAL_STATS_API)
@RestController
@RequestMapping("/dealStats")
public class DealStatsController extends BaseController {

    @Autowired
    private DealStatsService dealStatsService;

    @ApiOperation(value = DealStatsApi.listDealStats.METHOD_TITLE_NAME,
            notes = DealStatsApi.listDealStats.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listDealStats(@ApiParam(DealStatsApi.listDealStats.METHOD_API_USERNAME) @RequestParam(value = "userName", required = false) String userName,
                                   @ApiParam(DealStatsApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                   @ApiParam(DealStatsApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListDealStatsResultDTO> resultDTOS = dealStatsService.listDealStats(userName);
        return generatePage(resultDTOS);
    }
}
