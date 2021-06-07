package com.blockchain.server.aibot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.aibot.controller.api.TradingStrategyApi;
import com.blockchain.server.aibot.dto.TradingStrategyParamDTO;
import com.blockchain.server.aibot.service.TradingStrategyConfigService;
import com.blockchain.server.aibot.service.TradingStrategyService;
import com.blockchain.server.base.controller.BaseController;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author ljj
 *
 */
@Api(TradingStrategyApi.TRADING_STRATEGY_ACCOUNT_API)
@RestController
@RequestMapping("/tradingStrategy")
public class TradingStrategyController extends BaseController {

	@Autowired
	private TradingStrategyService tradingStrategyService;
	@Autowired
	private TradingStrategyConfigService tradingStrategyConfigService;

	@ApiOperation(value = TradingStrategyApi.list.METHOD_TITLE_NAME, notes = TradingStrategyApi.list.METHOD_TITLE_NOTE)
	@GetMapping("/list")
	public ResultDTO list(
			@ApiParam(TradingStrategyApi.list.METHOD_API_COIN) @RequestParam(value = "coin", required = false) String coin,
			@ApiParam(TradingStrategyApi.list.METHOD_API_STATE) @RequestParam(value = "state", required = false) String state,
			@ApiParam(TradingStrategyApi.list.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
			@ApiParam(TradingStrategyApi.list.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return generatePage(tradingStrategyConfigService.list(coin, state));
	}

	@ApiOperation(value = TradingStrategyApi.insert.METHOD_TITLE_NAME, notes = TradingStrategyApi.insert.METHOD_TITLE_NOTE)
	@PostMapping("/insert")
	public ResultDTO insertStrategy(
			@ApiParam(TradingStrategyApi.insert.METHOD_API_PARAM) TradingStrategyParamDTO paramDTO) {
		tradingStrategyConfigService.insert(paramDTO, getSysUserId(), getIpAddress());
		return ResultDTO.requstSuccess();
	}

	@ApiOperation(value = TradingStrategyApi.update.METHOD_TITLE_NAME, notes = TradingStrategyApi.update.METHOD_TITLE_NOTE)
	@PostMapping("/update")
	public ResultDTO update(@ApiParam(TradingStrategyApi.update.METHOD_API_PARAM) TradingStrategyParamDTO paramDTO) {
		tradingStrategyConfigService.update(paramDTO, getSysUserId(), getIpAddress());
		return ResultDTO.requstSuccess();
	}

	@ApiOperation(value = TradingStrategyApi.tradingStrategy.METHOD_TITLE_NAME, notes = TradingStrategyApi.tradingStrategy.METHOD_TITLE_NOTE)
	@PostMapping("/beginStrategyTransaction")
	public ResultDTO beginStrategyTransaction(
			@ApiParam(TradingStrategyApi.tradingStrategy.METHOD_API_ID) @RequestParam(value = "id", required = true) String id) {
		tradingStrategyService.tradingStrategy(id);
		return ResultDTO.requstSuccess();
	}

	@ApiOperation(value = TradingStrategyApi.tradingStrategy.METHOD_TITLE_NAME, notes = TradingStrategyApi.tradingStrategy.METHOD_TITLE_NOTE)
	@PostMapping("/closeStrategyTransaction")
	public ResultDTO closeStrategyTransaction(
			@ApiParam(TradingStrategyApi.tradingStrategy.METHOD_API_ID) @RequestParam(value = "id", required = true) String id) {
		tradingStrategyService.closeTradingStrategy(id);
		return ResultDTO.requstSuccess();
	}

	/***
	 * 获取管理员Id
	 * 
	 * @return
	 */
	private String getSysUserId() {
		return SecurityUtils.getUserId();
	}

	/***
	 * 获取操作者Ip地址
	 * 
	 * @return
	 */
	private String getIpAddress() {
		return HttpRequestUtil.getIpAddr();
	}
}
