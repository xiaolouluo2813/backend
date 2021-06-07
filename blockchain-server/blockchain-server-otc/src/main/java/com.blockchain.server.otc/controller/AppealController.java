package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.AppealApi;
import com.blockchain.server.otc.dto.appeal.ListAppealResultDTO;
import com.blockchain.server.otc.service.AppealService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(AppealApi.APPEAL_DETAIL_API)
@RestController
@RequestMapping("/appeal")
public class AppealController extends BaseController {

    @Autowired
    private AppealService appealService;

    @ApiOperation(value = AppealApi.listAppeal.METHOD_TITLE_NAME,
            notes = AppealApi.listAppeal.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listAppeal(@ApiParam(AppealApi.listAppeal.METHOD_API_ORDER_NUMBER) @RequestParam(value = "orderNumber", required = false) String orderNumber,
                                @ApiParam(AppealApi.listAppeal.METHOD_API_STATUS) @RequestParam(value = "status", required = false) String status,
                                @ApiParam(AppealApi.listAppeal.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                                @ApiParam(AppealApi.listAppeal.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                @ApiParam(AppealApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @ApiParam(AppealApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListAppealResultDTO> resultDTOS = appealService.listAppeal(orderNumber, status, beginTime, endTime);
        return generatePage(resultDTOS);
    }

    @ApiOperation(value = AppealApi.handleFinishOrder.METHOD_TITLE_NAME,
            notes = AppealApi.handleFinishOrder.METHOD_TITLE_NOTE)
    @PostMapping("/finishOrder")
    public ResultDTO handleFinishOrder(@ApiParam(AppealApi.handleFinishOrder.METHOD_API_APPEAL_ID) @RequestParam(value = "appealId") String appealId,
                                       @ApiParam(AppealApi.handleFinishOrder.METHOD_API_REMARK) @RequestParam(value = "remark") String remark) {
        appealService.handleFinishOrder(appealId, SecurityUtils.getUserId(), HttpRequestUtil.getIpAddr(), remark);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = AppealApi.handleCancelOrder.METHOD_TITLE_NAME,
            notes = AppealApi.handleCancelOrder.METHOD_TITLE_NOTE)
    @PostMapping("/cancelOrder")
    public ResultDTO handleCancelOrder(@ApiParam(AppealApi.handleCancelOrder.METHOD_API_APPEAL_ID) @RequestParam(value = "appealId") String appealId,
                                       @ApiParam(AppealApi.handleCancelOrder.METHOD_API_REMARK) @RequestParam(value = "remark") String remark) {
        appealService.handleCancelOrder(appealId, SecurityUtils.getUserId(), HttpRequestUtil.getIpAddr(), remark);
        return ResultDTO.requstSuccess();
    }
}
