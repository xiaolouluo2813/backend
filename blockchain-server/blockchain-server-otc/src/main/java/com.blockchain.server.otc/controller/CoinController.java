package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.CoinApi;
import com.blockchain.server.otc.dto.coin.ListCoinResultDTO;
import com.blockchain.server.otc.dto.coin.UpdateCoinParamDTO;
import com.blockchain.server.otc.service.CoinService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResultDTO listCoin(@ApiParam(CoinApi.listCoin.METHOD_API_COIN_NAME) @RequestParam(value = "coinName", required = false) String coinName,
                              @ApiParam(CoinApi.listCoin.METHOD_API_UNIT_NAME) @RequestParam(value = "unitName", required = false) String unitName,
                              @ApiParam(CoinApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                              @ApiParam(CoinApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListCoinResultDTO> resultDTOS = coinService.listCoin(coinName, unitName);
        return generatePage(resultDTOS);
    }

    @ApiOperation(value = CoinApi.updateCoin.METHOD_TITLE_NAME,
            notes = CoinApi.updateCoin.METHOD_TITLE_NOTE)
    @PostMapping("/update")
    public ResultDTO updateCoin(@ApiParam(CoinApi.updateCoin.METHOD_API_PARAM_DTO) UpdateCoinParamDTO paramDTO) {
        paramDTO.setSysUserId(SecurityUtils.getUserId());
        paramDTO.setIpAddress(HttpRequestUtil.getIpAddr());
        coinService.updateCoin(paramDTO);
        return ResultDTO.requstSuccess();
    }
}
