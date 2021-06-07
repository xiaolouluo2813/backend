package com.blockchain.server.databot.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.databot.controller.api.MatchConfigApi;
import com.blockchain.server.databot.dto.matchconfig.InsertMatchConfigParamDTO;
import com.blockchain.server.databot.dto.matchconfig.ListMatchConfigResultDTO;
import com.blockchain.server.databot.dto.matchconfig.UpdateMatchConfigParamDTO;
import com.blockchain.server.databot.service.MatchConfigService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(MatchConfigApi.MATCH_CONFIG_API)
@RestController
@RequestMapping("/matchConfig")
public class MatchConfigController extends BaseController {

    @Autowired
    private MatchConfigService matchConfigService;

    @ApiOperation(value = MatchConfigApi.list.METHOD_TITLE_NAME,
            notes = MatchConfigApi.list.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO list(@ApiParam(MatchConfigApi.list.METHOD_API_USERNAME)
                          @RequestParam(value = "userName", required = false) String userName,
                          @ApiParam(MatchConfigApi.list.METHOD_API_COINNAME)
                          @RequestParam(value = "coinName", required = false) String coinName,
                          @ApiParam(MatchConfigApi.list.METHOD_API_UNITNAME)
                          @RequestParam(value = "unitName", required = false) String unitName,
                          @ApiParam(MatchConfigApi.list.METHOD_API_STATUS)
                          @RequestParam(value = "status", required = false) String status,
                          @ApiParam(MatchConfigApi.list.METHOD_API_PRICE_TYPE)
                          @RequestParam(value = "priceType", required = false) String priceType,
                          @ApiParam(MatchConfigApi.list.METHOD_API_PAGENUM)
                          @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                          @ApiParam(MatchConfigApi.list.METHOD_API_PAGESIZE)
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListMatchConfigResultDTO> resultDTOS = matchConfigService.listConfig(userName, coinName, unitName, status, priceType);
        return generatePage(resultDTOS);
    }

    @ApiOperation(value = MatchConfigApi.insert.METHOD_TITLE_NAME,
            notes = MatchConfigApi.insert.METHOD_TITLE_NOTE)
    @PostMapping("/insert")
    public ResultDTO insert(@ApiParam(MatchConfigApi.insert.METHOD_API_PARAM_DTO) InsertMatchConfigParamDTO paramDTO) {
        matchConfigService.insertConfig(getSysUserId(), getIpAddress(), paramDTO);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = MatchConfigApi.update.METHOD_TITLE_NAME,
            notes = MatchConfigApi.update.METHOD_TITLE_NOTE)
    @PostMapping("/update")
    public ResultDTO update(@ApiParam(MatchConfigApi.update.METHOD_API_PARAM_DTO) UpdateMatchConfigParamDTO paramDTO) {
        matchConfigService.updateConfig(getSysUserId(), getIpAddress(), paramDTO);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = MatchConfigApi.delete.METHOD_TITLE_NAME,
            notes = MatchConfigApi.delete.METHOD_TITLE_NOTE)
    @PostMapping("/delete")
    public ResultDTO delete(@ApiParam(MatchConfigApi.delete.METHOD_API_ID) @RequestParam("id") String id) {
        matchConfigService.deleteConfig(getSysUserId(), getIpAddress(), id);
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
