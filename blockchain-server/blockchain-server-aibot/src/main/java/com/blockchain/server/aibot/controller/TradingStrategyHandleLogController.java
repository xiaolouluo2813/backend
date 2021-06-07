package com.blockchain.server.aibot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.aibot.controller.api.TradingStrategyHandleLogApi;
import com.blockchain.server.aibot.dto.TradingStrategyHandleLogResultDTO;
import com.blockchain.server.aibot.service.TradingStrategyHandleLogService;
import com.blockchain.server.base.controller.BaseController;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(TradingStrategyHandleLogApi.TRADING_STRATEGY_HANDLE_LOG_API)
@RestController
@RequestMapping("/tradingStrategyHandleLog")
public class TradingStrategyHandleLogController extends BaseController {

	@Autowired
	TradingStrategyHandleLogService tradingStrategyHandleLogService;

	@ApiOperation(value = TradingStrategyHandleLogApi.list.METHOD_TITLE_NAME, notes = TradingStrategyHandleLogApi.list.METHOD_TITLE_NOTE)
	@GetMapping("/list")
	public ResultDTO list(
			@ApiParam(TradingStrategyHandleLogApi.list.METHOD_API_HANDLE_TYPE) @RequestParam(value = "handleType", required = false) String handleType,
			@ApiParam(TradingStrategyHandleLogApi.list.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
			@ApiParam(TradingStrategyHandleLogApi.list.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
			@ApiParam(TradingStrategyHandleLogApi.list.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
			@ApiParam(TradingStrategyHandleLogApi.list.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TradingStrategyHandleLogResultDTO> resultDTOS = tradingStrategyHandleLogService
				.listTradingStrategyHandleLog(handleType, beginTime, endTime);
		return generatePage(resultDTOS);
	}
}
