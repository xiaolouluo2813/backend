package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.cct.controller.api.ConfigApi;
import com.blockchain.server.cct.entity.Config;
import com.blockchain.server.cct.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(ConfigApi.CONFIG_API)
@RestController
@RequestMapping("/config")
public class ConfigController extends BaseController {

    @Autowired
    private ConfigService configService;

    @ApiOperation(value = ConfigApi.updateConfig.METHOD_TITLE_NAME,
            notes = ConfigApi.updateConfig.METHOD_TITLE_NOTE)
    @PostMapping("/update")
    public ResultDTO updateConfig(@ApiParam(ConfigApi.updateConfig.METHOD_API_TAG) @RequestParam(value = "tag", required = false) String tag,
                                  @ApiParam(ConfigApi.updateConfig.METHOD_API_KEY) @RequestParam("key") String key,
                                  @ApiParam(ConfigApi.updateConfig.METHOD_API_VAL) @RequestParam(value = "val", required = false) String val,
                                  @ApiParam(ConfigApi.updateConfig.METHOD_API_STATUS) @RequestParam(value = "status", required = false) String status) {
        configService.updateConfig(SecurityUtils.getUserId(), HttpRequestUtil.getIpAddr(), tag, key, val, status);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = ConfigApi.UpdateCommissionIssueTime.METHOD_TITLE_NAME,
            notes = ConfigApi.UpdateCommissionIssueTime.METHOD_TITLE_NOTE)
    @PostMapping("/updateCommissionIssueTime")
    public ResultDTO updateCommissionIssueTime(@ApiParam(ConfigApi.UpdateCommissionIssueTime.METHOD_API_TYPE) @RequestParam(value = "type", required = false) String type,
                                               @ApiParam(ConfigApi.UpdateCommissionIssueTime.METHOD_API_DAY) @RequestParam(value = "day", required = false) Integer day,
                                               @ApiParam(ConfigApi.UpdateCommissionIssueTime.METHOD_API_HOUR) @RequestParam(value = "hour", required = false) Integer hour,
                                               @ApiParam(ConfigApi.UpdateCommissionIssueTime.METHOD_API_STATUS) @RequestParam(value = "status", required = false) String status) {
        configService.updateCommissionIssueTime(SecurityUtils.getUserId(), HttpRequestUtil.getIpAddr(), type, day, hour, status);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = ConfigApi.listConfig.METHOD_TITLE_NAME,
            notes = ConfigApi.updateConfig.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listConfig() {
        List<Config> configs = configService.listConfig();
        return generatePage(configs);
    }
}
