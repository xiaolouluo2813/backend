package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.otc.controller.api.AppealImgApi;
import com.blockchain.server.otc.dto.appealimg.ListAppealImgResultDTO;
import com.blockchain.server.otc.service.AppealImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(AppealImgApi.APPEAL_IMG_API)
@RestController
@RequestMapping("/appealImg")
public class AppealImgController {

    @Autowired
    private AppealImgService appealImgService;

    @ApiOperation(value = AppealImgApi.listAppealImgByAppealDetailId.METHOD_TITLE_NAME,
            notes = AppealImgApi.listAppealImgByAppealDetailId.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listAppealImgByAppealDetailId(@ApiParam(AppealImgApi.listAppealImgByAppealDetailId.METHOD_API_APPEAL_DETAIL_ID)
                                                   @RequestParam(value = "appealDetailId") String appealDetailId) {
        List<ListAppealImgResultDTO> resultDTOS = appealImgService.listAppealImgByAppealDetailId(appealDetailId);
        return ResultDTO.requstSuccess(resultDTOS);
    }
}
