package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.AppealDetailApi;
import com.blockchain.server.otc.dto.appealdetail.ListAppealDetailResultDTO;
import com.blockchain.server.otc.service.AppealDetailService;
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

@Api(AppealDetailApi.APPEAL_DETAIL_API)
@RestController
@RequestMapping("/appealDetail")
public class AppealDetailController extends BaseController {

    @Autowired
    private AppealDetailService appealDetailService;

    @ApiOperation(value = AppealDetailApi.listAppealDetailByAppealId.METHOD_TITLE_NAME,
            notes = AppealDetailApi.listAppealDetailByAppealId.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listAppealDetailByAppealId(@ApiParam(AppealDetailApi.listAppealDetailByAppealId.METHOD_API_APPEAL_ID) @RequestParam(value = "appealId") String appealId,
                                                @ApiParam(AppealDetailApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                @ApiParam(AppealDetailApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListAppealDetailResultDTO> resultDTOS = appealDetailService.listAppealDetailByAppealId(appealId);
        return generatePage(resultDTOS);
    }
}
