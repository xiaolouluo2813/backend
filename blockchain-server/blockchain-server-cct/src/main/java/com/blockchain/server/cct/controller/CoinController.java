package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.cct.controller.api.CoinApi;
import com.blockchain.server.cct.dto.coin.CoinParamDTO;
import com.blockchain.server.cct.entity.Coin;
import com.blockchain.server.cct.service.CoinService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(CoinApi.COIN_API)
@RestController
@RequestMapping("/coin")
public class CoinController extends BaseController {

    @Autowired
    private CoinService coinService;

    @ApiOperation(value = CoinApi.listCoin.METHOD_TITLE_NAME,
            notes = CoinApi.listCoin.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listCoin(@ApiParam(CoinApi.listCoin.METHOD_API_COINNAME) @RequestParam(value = "coinName", required = false) String coinName,
                              @ApiParam(CoinApi.listCoin.METHOD_API_UNITNAME) @RequestParam(value = "unitName", required = false) String unitName,
                              @ApiParam(CoinApi.listCoin.METHOD_API_STATUS) @RequestParam(value = "status", required = false) String status,
                              @ApiParam(CoinApi.listCoin.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                              @ApiParam(CoinApi.listCoin.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        //分页
        PageHelper.startPage(pageNum, pageSize);
        List<Coin> coins = coinService.listCoin(coinName, unitName, status);
        return generatePage(coins);
    }

    @ApiOperation(value = CoinApi.updateCoin.METHOD_TITLE_NAME,
            notes = CoinApi.updateCoin.METHOD_TITLE_NOTE)
    @PostMapping("/update")
    public ResultDTO updateCoin(@ApiParam(CoinApi.updateCoin.METHOD_API_PARAM) CoinParamDTO param) {
        param.setIpAddr(HttpRequestUtil.getIpAddr());
        param.setSysUserId(SecurityUtils.getUserId());
        coinService.updateCoin(param);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = CoinApi.insertCoin.METHOD_TITLE_NAME,
            notes = CoinApi.insertCoin.METHOD_TITLE_NOTE)
    @PostMapping("/insert")
    public ResultDTO insertCoin(@ApiParam(CoinApi.insertCoin.METHOD_API_PARAM) CoinParamDTO param) {
        param.setIpAddr(HttpRequestUtil.getIpAddr());
        param.setSysUserId(SecurityUtils.getUserId());
        coinService.insertCoin(param);
        return ResultDTO.requstSuccess();
    }
}
