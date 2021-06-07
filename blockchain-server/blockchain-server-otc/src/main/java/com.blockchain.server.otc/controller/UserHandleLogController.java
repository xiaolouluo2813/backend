package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.UserHandleLogApi;
import com.blockchain.server.otc.dto.userhandlelog.ListUserHandleLogResultDTO;
import com.blockchain.server.otc.service.UserHandleLogService;
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

@Api(UserHandleLogApi.USER_HANDLE_LOG_API)
@RestController
@RequestMapping("/userHandleLog")
public class UserHandleLogController extends BaseController {

    @Autowired
    private UserHandleLogService userHandleLogService;

    @ApiOperation(value = UserHandleLogApi.listUserHandleLog.METHOD_TITLE_NAME,
            notes = UserHandleLogApi.listUserHandleLog.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listUserHandleLog(@ApiParam(UserHandleLogApi.listUserHandleLog.METHOD_API_USERNAME) @RequestParam(value = "userName", required = false) String userName,
                                       @ApiParam(UserHandleLogApi.listUserHandleLog.METHOD_API_HANDLE_NUMBER) @RequestParam(value = "handleNumber", required = false) String handleNumber,
                                       @ApiParam(UserHandleLogApi.listUserHandleLog.METHOD_API_HANDLE_NUMBER_TYPE) @RequestParam(value = "handleNumberType", required = false) String handleNumberType,
                                       @ApiParam(UserHandleLogApi.listUserHandleLog.METHOD_API_HANDLE_TYPE) @RequestParam(value = "handleType", required = false) String handleType,
                                       @ApiParam(UserHandleLogApi.listUserHandleLog.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                                       @ApiParam(UserHandleLogApi.listUserHandleLog.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                       @ApiParam(UserHandleLogApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @ApiParam(UserHandleLogApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListUserHandleLogResultDTO> resultDTOS = userHandleLogService.listUserHandleLog(userName, handleNumber, handleNumberType, handleType, beginTime, endTime);
        return generatePage(resultDTOS);
    }
}
