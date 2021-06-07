package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.ConfigApi;
import com.blockchain.server.otc.dto.config.ListConfigResultDTO;
import com.blockchain.server.otc.service.ConfigService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(ConfigApi.CONFIG_API)
@RestController
@RequestMapping("/config")
public class ConfigController extends BaseController {

    @Autowired
    private ConfigService configService;

    @ApiOperation(value = ConfigApi.listConfig.METHOD_TITLE_NAME,
            notes = ConfigApi.listConfig.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listConfig(@ApiParam(ConfigApi.listConfig.METHOD_API_DATA_KEY) @RequestParam(value = "dataKeys", required = false) String[] dataKeys,
                                @ApiParam(ConfigApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @ApiParam(ConfigApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListConfigResultDTO> resultDTOS = configService.listConfig();
        return generatePage(resultDTOS);
    }

    @ApiOperation(value = ConfigApi.updateConfig.METHOD_TITLE_NAME,
            notes = ConfigApi.updateConfig.METHOD_TITLE_NOTE)
    @PostMapping("/update")
    public ResultDTO updateConfig(@ApiParam(ConfigApi.updateConfig.METHOD_API_ID) @RequestParam(value = "id") String id,
                                  @ApiParam(ConfigApi.updateConfig.METHOD_API_DATA_VALUE) @RequestParam(value = "dataValue", required = false) String dataValue,
                                  @ApiParam(ConfigApi.updateConfig.METHOD_API_STATUS) @RequestParam(value = "status", required = false) String status) {
        configService.updateConfig(SecurityUtils.getUserId(), HttpRequestUtil.getIpAddr(), id, dataValue, status);
        return ResultDTO.requstSuccess();
    }
}
