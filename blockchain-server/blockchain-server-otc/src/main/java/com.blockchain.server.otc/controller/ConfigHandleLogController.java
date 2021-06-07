package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.ConfigHandleLogApi;
import com.blockchain.server.otc.dto.confighandlelog.ListConfigHandleLogResultDTO;
import com.blockchain.server.otc.service.ConfigHandleLogService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(ConfigHandleLogApi.CONFIG_HANDLE_LOG_API)
@RestController
@RequestMapping("/configHandleLog")
public class ConfigHandleLogController extends BaseController {

    @Autowired
    private ConfigHandleLogService configHandleLogService;

    @ApiOperation(value = ConfigHandleLogApi.listConfigHandleLog.METHOD_TITLE_NAME,
            notes = ConfigHandleLogApi.listConfigHandleLog.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listConfigHandleLog(@ApiParam(ConfigHandleLogApi.listConfigHandleLog.METHOD_API_DATA_KEY) @RequestParam(value = "dataKey", required = false) String dataKey,
                                         @ApiParam(ConfigHandleLogApi.listConfigHandleLog.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                                         @ApiParam(ConfigHandleLogApi.listConfigHandleLog.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                         @ApiParam(ConfigHandleLogApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @ApiParam(ConfigHandleLogApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListConfigHandleLogResultDTO> resultDTOS = configHandleLogService.listConfigHandleLog(dataKey, beginTime, endTime);
        return generatePage(resultDTOS);
    }

}
