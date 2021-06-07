package com.blockchain.server.databot.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.databot.controller.api.CurrencyConfigApi;
import com.blockchain.server.databot.dto.currencyconfig.InsertConfigParamDTO;
import com.blockchain.server.databot.dto.currencyconfig.ListConfigResultDTO;
import com.blockchain.server.databot.dto.currencyconfig.UpdateConfigParamDTO;
import com.blockchain.server.databot.service.CurrencyConfigService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(CurrencyConfigApi.CURRENCY_CONFIG_API)
@RestController
@RequestMapping("/config")
public class CurrencyConfigController extends BaseController {

    @Autowired
    private CurrencyConfigService configService;

    @ApiOperation(value = CurrencyConfigApi.list.METHOD_TITLE_NAME,
            notes = CurrencyConfigApi.list.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listConfig(@ApiParam(CurrencyConfigApi.list.METHOD_API_CURRENCY_PAIR)
                                @RequestParam(value = "currencyPair", required = false) String currencyPair,
                                @ApiParam(CurrencyConfigApi.list.METHOD_API_STATUS)
                                @RequestParam(value = "status", required = false) String status,
                                @ApiParam(CurrencyConfigApi.list.METHOD_API_PAGENUM)
                                @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                @ApiParam(CurrencyConfigApi.list.METHOD_API_PAGESIZE)
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListConfigResultDTO> resultDTOS = configService.listConfig(currencyPair, status);
        return generatePage(resultDTOS);
    }

    @ApiOperation(value = CurrencyConfigApi.insertConfig.METHOD_TITLE_NAME,
            notes = CurrencyConfigApi.insertConfig.METHOD_TITLE_NOTE)
    @PostMapping("/insert")
    public ResultDTO insertConfig(@ApiParam(CurrencyConfigApi.insertConfig.METHOD_API_PARAM_DTO) InsertConfigParamDTO paramDTO) {
        configService.insertConfig(paramDTO, getSysUserId(), getIpAddress());
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = CurrencyConfigApi.updateConfig.METHOD_TITLE_NAME,
            notes = CurrencyConfigApi.updateConfig.METHOD_TITLE_NOTE)
    @PostMapping("/update")
    public ResultDTO updateConfig(@ApiParam(CurrencyConfigApi.updateConfig.METHOD_API_PARAM_DTO) UpdateConfigParamDTO paramDTO) {
        configService.updateConfig(paramDTO, getSysUserId(), getIpAddress());
        return ResultDTO.requstSuccess();
    }

    /***
     * 获取管理员Id
     * @return
     */
    private String getSysUserId() {
        return SecurityUtils.getUserId();
    }

    /***
     * 获取操作者Ip地址
     * @return
     */
    private String getIpAddress() {
        return HttpRequestUtil.getIpAddr();
    }

}
