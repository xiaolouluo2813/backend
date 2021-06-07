package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.PageDTO;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.cct.controller.api.CommissionApi;
import com.blockchain.server.cct.dto.commission.ListCommissionResultDTO;
import com.blockchain.server.cct.schedule.CommissionSchedule;
import com.blockchain.server.cct.service.CommissionService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(CommissionApi.COMMISSION_API)
@RestController
@RequestMapping("/commission")
public class CommissionController extends BaseController {

    @Autowired
    private CommissionService commissionService;
    @Autowired
    private CommissionSchedule commissionSchedule;

    @ApiOperation(value = CommissionApi.List.METHOD_TITLE_NAME,
            notes = CommissionApi.List.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO<PageDTO> list(@ApiParam(CommissionApi.List.METHOD_API_USER_NAME) @RequestParam(value = "userName", required = false) String userName,
                                   @ApiParam(CommissionApi.List.METHOD_API_P_USER_NAME) @RequestParam(value = "puserName", required = false) String puserName,
                                   @ApiParam(CommissionApi.List.METHOD_API_COINNAME) @RequestParam(value = "coinName", required = false) String coinName,
                                   @ApiParam(CommissionApi.List.METHOD_API_STATUS) @RequestParam(value = "status", required = false) String status,
                                   @ApiParam(CommissionApi.List.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                   @ApiParam(CommissionApi.List.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListCommissionResultDTO> resultDTOS = commissionService.list(userName, puserName, coinName, status);
        return generatePage(resultDTOS);
    }

    @ApiOperation(value = CommissionApi.IssueCommission.METHOD_TITLE_NAME,
            notes = CommissionApi.IssueCommission.METHOD_TITLE_NOTE)
    @PostMapping("/issueCommission")
    public ResultDTO issueCommission() {
        //验证一下是否有登陆
        SecurityUtils.getUserId();

        int pageNum = 1;
        int pageSize = 10;
        commissionSchedule.issueCommission(pageNum, pageSize);
        return ResultDTO.requstSuccess();
    }
}
