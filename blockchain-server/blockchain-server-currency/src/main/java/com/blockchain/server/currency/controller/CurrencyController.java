package com.blockchain.server.currency.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.currency.controller.api.CurrencyApi;
import com.blockchain.server.currency.entity.Currency;
import com.blockchain.server.currency.service.CurrencyService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(CurrencyApi.CURRENCY_API)
@RestController
@RequestMapping("/currency")
public class CurrencyController extends BaseController {

    @Autowired
    private CurrencyService currencyService;

    /***
     * 查询所有币种信息
     * @param coinName
     * @param status
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ApiOperation(value = CurrencyApi.listCurrency.METHOD_TITLE_NAME,
            notes = CurrencyApi.listCurrency.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listCurrency(@ApiParam(CurrencyApi.listCurrency.METHOD_TITLE_COINNAME) @RequestParam(value = "coinName", required = false) String coinName,
                                  @ApiParam(CurrencyApi.listCurrency.METHOD_TITLE_STATUS) @RequestParam(value = "status", required = false) Integer status,
                                  @ApiParam(CurrencyApi.listCurrency.METHOD_TITLE_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  @ApiParam(CurrencyApi.listCurrency.METHOD_TITLE_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Currency> currencies = currencyService.listCurrency(coinName, status);
        return generatePage(currencies);
    }

    @ApiOperation(value = CurrencyApi.insert.METHOD_TITLE_NAME,
            notes = CurrencyApi.insert.METHOD_TITLE_NOTE)
    @PostMapping("/insert")
    public ResultDTO insert(Currency currency) {
        currencyService.insertCurrency(currency);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = CurrencyApi.update.METHOD_TITLE_NAME,
            notes = CurrencyApi.update.METHOD_TITLE_NOTE)
    @PostMapping("/update")
    public ResultDTO update(Currency currency) {
        currencyService.updateCurrency(currency);
        return ResultDTO.requstSuccess();
    }
}
