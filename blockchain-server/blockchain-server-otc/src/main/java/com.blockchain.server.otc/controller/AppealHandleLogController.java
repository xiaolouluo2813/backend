package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.AppealHandleLogApi;
import com.blockchain.server.otc.dto.appealhandlelog.ListAppealHandleLogResultDTO;
import com.blockchain.server.otc.service.AppealHandleLogService;
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

@Api(AppealHandleLogApi.APPEAL_HANDLE_LOG_API)
@RestController
@RequestMapping("/appealHandleLog")
public class AppealHandleLogController extends BaseController {

    @Autowired
    private AppealHandleLogService appealHandleLogService;

    @ApiOperation(value = AppealHandleLogApi.listAppealHandleLog.METHOD_TITLE_NAME,
            notes = AppealHandleLogApi.listAppealHandleLog.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listAppealHandleLog(@ApiParam(AppealHandleLogApi.listAppealHandleLog.METHOD_API_ORDER_NUMBER) @RequestParam(value = "orderNumber", required = false) String orderNumber,
                                         @ApiParam(AppealHandleLogApi.listAppealHandleLog.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                                         @ApiParam(AppealHandleLogApi.listAppealHandleLog.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                         @ApiParam(AppealHandleLogApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @ApiParam(AppealHandleLogApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListAppealHandleLogResultDTO> resultDTOS = appealHandleLogService.listAppealHandleLog(orderNumber, beginTime, endTime);
        return generatePage(resultDTOS);
    }
}
