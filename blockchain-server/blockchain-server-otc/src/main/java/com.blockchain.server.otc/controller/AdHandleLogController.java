package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.AdHandleLogApi;
import com.blockchain.server.otc.dto.adhandlelog.ListAdHandleLogResultDTO;
import com.blockchain.server.otc.service.AdHandleLogService;
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

@Api(AdHandleLogApi.AD_HANDLE_LOG_API)
@RestController
@RequestMapping("/adHandleLog")
public class AdHandleLogController extends BaseController {

    @Autowired
    private AdHandleLogService adHandleLogService;

    @ApiOperation(value = AdHandleLogApi.listAdHandleLog.METHOD_TITLE_NAME,
            notes = AdHandleLogApi.listAdHandleLog.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listAdHandleLog(@ApiParam(AdHandleLogApi.listAdHandleLog.METHOD_API_AD_NUMBER) @RequestParam(value = "adNumber", required = false) String adNumber,
                                     @ApiParam(AdHandleLogApi.listAdHandleLog.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                                     @ApiParam(AdHandleLogApi.listAdHandleLog.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                     @ApiParam(AdHandleLogApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @ApiParam(AdHandleLogApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListAdHandleLogResultDTO> resultDTOS = adHandleLogService.listAdHandleLog(adNumber, beginTime, endTime);
        return generatePage(resultDTOS);
    }
}
