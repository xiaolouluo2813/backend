package com.blockchain.server.databot.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.databot.controller.api.MatchConfigHandleLogApi;
import com.blockchain.server.databot.dto.matchconfig.ListMatchConfigHandleLogResultDTO;
import com.blockchain.server.databot.service.MatchConfigHandleLogService;
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

@Api(MatchConfigHandleLogApi.MATCH_CONFIG_API)
@RestController
@RequestMapping("/matchConfigHandleLog")
public class MatchConfigHandleLogController extends BaseController {

    @Autowired
    private MatchConfigHandleLogService configHandleLogService;

    @ApiOperation(value = MatchConfigHandleLogApi.list.METHOD_TITLE_NAME,
            notes = MatchConfigHandleLogApi.list.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO list(@ApiParam(MatchConfigHandleLogApi.list.METHOD_API_HANDLE_TYPE)
                          @RequestParam(value = "handleType", required = false) String handleType,
                          @ApiParam(MatchConfigHandleLogApi.list.METHOD_API_BEGIN_TIME)
                          @RequestParam(value = "beginTime", required = false) String beginTime,
                          @ApiParam(MatchConfigHandleLogApi.list.METHOD_API_END_TIME)
                          @RequestParam(value = "endTime", required = false) String endTime,
                          @ApiParam(MatchConfigHandleLogApi.list.METHOD_API_PAGENUM)
                          @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                          @ApiParam(MatchConfigHandleLogApi.list.METHOD_API_PAGESIZE)
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListMatchConfigHandleLogResultDTO> resultDTOS = configHandleLogService.listHandleLog(handleType, beginTime, endTime);
        return generatePage(resultDTOS);
    }
}
