package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.cct.controller.api.LogApi;
import com.blockchain.server.cct.entity.CoinLog;
import com.blockchain.server.cct.entity.ConfigLog;
import com.blockchain.server.cct.entity.PublishOrderLog;
import com.blockchain.server.cct.service.CoinLogService;
import com.blockchain.server.cct.service.ConfigLogService;
import com.blockchain.server.cct.service.PublishOrderLogService;
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

@Api(LogApi.LOG_API)
@RestController
@RequestMapping("/log")
public class LogController extends BaseController {

    @Autowired
    private ConfigLogService configLogService;
    @Autowired
    private CoinLogService coinLogService;
    @Autowired
    private PublishOrderLogService orderLogService;

    @ApiOperation(value = LogApi.listCoinLog.METHOD_TITLE_NAME,
            notes = LogApi.listCoinLog.METHOD_TITLE_NOTE)
    @GetMapping("/listCoinLog")
    public ResultDTO listCoinLog(@ApiParam(LogApi.listCoinLog.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                 @ApiParam(LogApi.listCoinLog.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CoinLog> lists = coinLogService.listCoinLog();
        return generatePage(lists);
    }

    @ApiOperation(value = LogApi.listOrderLog.METHOD_TITLE_NAME,
            notes = LogApi.listOrderLog.METHOD_TITLE_NOTE)
    @GetMapping("/listOrderLog")
    public ResultDTO listOrderLog(@ApiParam(LogApi.listOrderLog.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @ApiParam(LogApi.listOrderLog.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PublishOrderLog> lists = orderLogService.listOrderLog();
        return generatePage(lists);
    }

    @ApiOperation(value = LogApi.listConfigLog.METHOD_TITLE_NAME,
            notes = LogApi.listConfigLog.METHOD_TITLE_NOTE)
    @GetMapping("/listConfigLog")
    public ResultDTO listConfigLog(@ApiParam(LogApi.listConfigLog.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                   @ApiParam(LogApi.listConfigLog.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ConfigLog> lists = configLogService.listConfigLog();
        return generatePage(lists);
    }

}
