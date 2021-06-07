package com.blockchain.server.currency.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.currency.controller.api.CurrencyApi;
import com.blockchain.server.currency.controller.api.CurrencyPairApi;
import com.blockchain.server.currency.entity.CurrencyPair;
import com.blockchain.server.currency.service.CurrencyPairService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(CurrencyPairApi.CURRENCY_PAIR_API)
@RestController
@RequestMapping("/currencyPair")
public class CurrencyPairController extends BaseController {

    @Autowired
    private CurrencyPairService currencyPairService;

    @ApiOperation(value = CurrencyPairApi.listCurrencyPair.METHOD_TITLE_NAME,
            notes = CurrencyPairApi.listCurrencyPair.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listCurrencyPair(@ApiParam(CurrencyPairApi.listCurrencyPair.METHOD_TITLE_CURRENCY_PAIR) @RequestParam(value = "currencyPair", required = false) String currencyPair,
                                      @ApiParam(CurrencyPairApi.listCurrencyPair.METHOD_TITLE_STATUS) @RequestParam(value = "status", required = false) Integer status,
                                      @ApiParam(CurrencyPairApi.listCurrencyPair.METHOD_TITLE_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                      @ApiParam(CurrencyPairApi.listCurrencyPair.METHOD_TITLE_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<CurrencyPair> currencyPairs = currencyPairService.listCurrencyPair(currencyPair, status);
        return generatePage(currencyPairs);
    }

    @ApiOperation(value = CurrencyPairApi.insert.METHOD_TITLE_NAME,
            notes = CurrencyPairApi.insert.METHOD_TITLE_NOTE)
    @PostMapping("/insert")
    public ResultDTO insert(@ApiParam(CurrencyPairApi.insert.METHOD_TITLE_CURRENCY_PAIR) @RequestParam("currencyPair") String currencyPair,
                            @ApiParam(CurrencyPairApi.insert.METHOD_TITLE_STATUS) @RequestParam("status") Integer status,
                            @ApiParam(CurrencyPairApi.insert.METHOD_TITLE_ORDERBY) @RequestParam("orderBy") Integer orderBy,
                            @ApiParam(CurrencyPairApi.insert.METHOD_TITLE_ISHOME) @RequestParam("isHome") Integer isHome,
                            @ApiParam(CurrencyPairApi.insert.METHOD_TITLE_ISCCT) @RequestParam("isCct") Integer isCct) {
        currencyPairService.insertCurrencyPair(currencyPair, status, orderBy, isHome, isCct);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = CurrencyPairApi.update.METHOD_TITLE_NAME,
            notes = CurrencyPairApi.update.METHOD_TITLE_NOTE)
    @PostMapping("/update")
    public ResultDTO update(@ApiParam(CurrencyPairApi.update.METHOD_TITLE_CURRENCY_PAIR) @RequestParam("currencyPair") String currencyPair,
                            @ApiParam(CurrencyPairApi.update.METHOD_TITLE_STATUS) @RequestParam(value = "status", required = false) Integer status,
                            @ApiParam(CurrencyPairApi.update.METHOD_TITLE_ORDERBY) @RequestParam(value = "orderBy", required = false) Integer orderBy,
                            @ApiParam(CurrencyPairApi.update.METHOD_TITLE_ISHOME) @RequestParam(value = "isHome", required = false) Integer isHome,
                            @ApiParam(CurrencyPairApi.update.METHOD_TITLE_ISCCT) @RequestParam(value = "isCct", required = false) Integer isCct) {
        String sysUserId = SecurityUtils.getUserId();
        String ipAddr = HttpRequestUtil.getIpAddr();
        currencyPairService.updateCurrencyPair(currencyPair, status, orderBy, isHome, isCct, sysUserId, ipAddr);
        return ResultDTO.requstSuccess();
    }
}
